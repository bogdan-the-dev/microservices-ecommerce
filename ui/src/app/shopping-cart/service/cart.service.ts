import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ItemCartModel} from "../model/item-cart.model";
import * as http from "http";
import {CartApiUrls} from "./cart.api-urls";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  constructor(private http: HttpClient) {
  }

  addItem(item: ItemCartModel) {
    return this.http.put(CartApiUrls.addItem, {itemId: item.id, quantity: item.quantity})
  }

  removeItem(item: ItemCartModel) {
    return this.http.delete(CartApiUrls.removeItem + '?itemId=' + item.id)
  }

  addItemLocally(item: ItemCartModel) {
    let array: {itemId: string, quantity: number}[] = JSON.parse(localStorage.getItem("shoppingCart"))
    if(array === null) {
      array = []
    }
    let itemPresent = false
      array = array.map(elem => {
        if (elem.itemId == item.id) {
          elem.quantity += item.quantity
          itemPresent = true
        }
        return elem
      })
    if (!itemPresent){
      array.push({itemId: item.id, quantity: item.quantity})
    }
    localStorage.setItem("shoppingCart", JSON.stringify(array))
  }

  removeItemLocally(item: ItemCartModel) {
    let array: {itemId: string, quantity: number}[] = JSON.parse(localStorage.getItem("shoppingCart"))
    array = array.filter(elem => elem.itemId !== item.id)
    localStorage.setItem("shoppingCart", JSON.stringify(array))
  }

  emptyCart() {
    return this.http.delete(CartApiUrls.emptyCart)
  }

  emptyCartLocally() {
    localStorage.removeItem("shoppingCart")
  }

  getCart() {
    return this.http.get<{itemId: string, quantity: number}[]>(CartApiUrls.getCart)
  }

  getCartLocally() {
    const array: {itemId: string, quantity: number}[] = JSON.parse(localStorage.getItem("shoppingCart"))
    if(array === null || array === undefined) {
      return []
    }
    return array
  }

}
