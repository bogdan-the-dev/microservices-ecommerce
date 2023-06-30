import {ApiUrls} from "../../shared/service/api-urls";

export class CartApiUrls {
  static readonly baseCartUrl = ApiUrls.serverUrl + '/api/v1/cart';
  static readonly getCart = CartApiUrls.baseCartUrl + '/get-cart'
  static readonly addItem = CartApiUrls.baseCartUrl + '/add-item'
  static readonly removeItem = CartApiUrls.baseCartUrl + '/remove-item'
  static readonly emptyCart = CartApiUrls.baseCartUrl + '/empty-cart'

}
