import {Component, OnInit} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {select, Store} from "@ngrx/store";
import {filter} from "rxjs";
import {ItemCartModel} from "../../model/item-cart.model";
import {CartAction} from "../../state-management/cart.action";
import {Router} from "@angular/router";

@Component({
  selector: 'app-cart-mini',
  templateUrl: 'cart.component.html',
  styleUrls: ['cart.component.less']
})
export class CartComponent extends BaseComponent implements OnInit {
  count: number = 0;
  cartItems: ItemCartModel[] = []
  total: number = 0
  constructor(private store: Store<any>, private router: Router) {
    super();
  }

  override ngOnInit() {
    super.ngOnInit();
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.cartModuleFeature.cartState.numberOfItems),
        filter(s => s !== null)
      ).subscribe(numberOfItems => {
        this.count = numberOfItems
      })
    )
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.cartModuleFeature.cartState.items),
        filter(s => s !== null)
      ).subscribe(items => {
        this.cartItems = items
      })
    )
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.cartModuleFeature.cartState.total),
        filter(s=> s!== null)
      ).subscribe(total => {
        this.total = total
      })
    )
  }

  placeOrder(){
    this.router.navigate(['/order/checkout'])
  }

  removeItem(item: ItemCartModel) {
    this.store.dispatch({type: CartAction.REMOVE_FROM_CART, payload: item})
  }

  emptyCart() {
    this.store.dispatch({type: CartAction.EMPTY_CART, payload: {}})
  }

}
