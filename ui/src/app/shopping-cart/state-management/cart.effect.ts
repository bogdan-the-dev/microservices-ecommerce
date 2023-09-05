import {Injectable} from "@angular/core";
import {act, Actions, Effect, ofType} from "@ngrx/effects";
import {CartService} from "../service/cart.service";
import {CartAction} from "./cart.action";
import {Convert} from "../../shared/utils/convert";
import {map, of, switchMap, withLatestFrom} from "rxjs";
import {Store} from "@ngrx/store";
import {ProductsService} from "../../products/service/products.service";
import {ItemCartModel} from "../model/item-cart.model";


@Injectable()
export class CartEffect {
  constructor(private actions: Actions, private service: CartService, private store: Store<any>, private productService: ProductsService) {
  }

  @Effect()
  addItem = this.actions.pipe(
    ofType(CartAction.ADD_TO_CART),
    map(Convert.ToPayload),
    withLatestFrom(this.store.select(s => s.cartModuleFeature.cartState.saveOnline)),
    switchMap(([payload, saveOnline]) => {
      if(saveOnline) {
        return this.service.addItem(payload)
          .pipe(
            switchMap(result => {
              return of({type: CartAction.ADD_TO_CART_FINISHED, payload: payload})
            })
          )
      } else {
        this.service.addItemLocally(payload)
        return of({type: CartAction.ADD_TO_CART_FINISHED, payload: payload})
      }
    })
  )

  @Effect()
  removeItem = this.actions.pipe(
    ofType(CartAction.REMOVE_FROM_CART),
    map(Convert.ToPayload),
    withLatestFrom(this.store.select(s => s.cartModuleFeature.cartState.saveOnline)),
    switchMap(([payload, saveOnline]) => {
      if(saveOnline) {
        this.service.removeItem(payload)
          .pipe(
            switchMap(result => {
              return of({type: CartAction.REMOVE_FROM_CART_FINISHED, payload: payload})
            })
          )
      } else {
        this.service.addItemLocally(payload)
      }
      return of({type: CartAction.REMOVE_FROM_CART_FINISHED, payload: payload})
    })
  )

  @Effect()
  getCart = this.actions.pipe(
    ofType(CartAction.GET_CART),
    map(Convert.ToPayload),
    switchMap(payload => {
      return this.service.getCart().pipe(
        switchMap(res => {
          return of({type: CartAction.GET_CART_FINISHED, payload: res})
        }))
    }))
      // else {
      //   const data = this.service.getCartLocally()
      //   ids = data.map(elem => elem.itemId)
      //   qty = data.map(elem => elem.quantity)
      //   return this.productService.getProductForCart(ids)
      //     .pipe(
      //       switchMap((products: {itemId: string, title: string, image: string, price: number}[]) => {
      //         const cartProduct: ItemCartModel[] = []
      //         for(let i = 0; i < products.length; i++) {
      //           cartProduct.push({...products[i], quantity: qty[i], id: products[i].itemId})
      //         }
      //         return of({type: CartAction.GET_CART_FINISHED, payload: cartProduct})
      //       })
      //     )
      // }

  @Effect()
  emptyCart = this.actions.pipe(
    ofType(CartAction.EMPTY_CART),
    map(Convert.ToPayload),
    withLatestFrom(this.store.select(s => s.cartModuleFeature.cartState.saveOnline)),
    //@ts-ignore
    switchMap(([_, saveOnline]) => {
      if(saveOnline) {
      //   return this.service.emptyCart()
      //     .pipe(
      //       switchMap(res => {
      //         return of({type: CartAction.EMPTY_CART_FINISHED, payload: {}})
      //       })
      //     )
       }
      // this.service.emptyCartLocally()
      return of({type: CartAction.EMPTY_CART_FINISHED, payload: {}})
    })
  )


}
