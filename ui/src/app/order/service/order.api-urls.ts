import {ApiUrls} from "../../shared/service/api-urls";

export class OrderApiUrls {
  static baseOrderUrl = ApiUrls.serverUrl + '/api/v1/order'
  static placeOrder = OrderApiUrls.baseOrderUrl + '/place'
  static cancel = OrderApiUrls.baseOrderUrl + '/cancel'
  static pay = OrderApiUrls.baseOrderUrl + '/pay'
  static getAll = OrderApiUrls.baseOrderUrl + '/get-all'
  static getForUser = OrderApiUrls.baseOrderUrl + '/get-for-user'
  static seeOrder = OrderApiUrls.baseOrderUrl + '/get'
  static changeStatus = OrderApiUrls.baseOrderUrl + '/edit'
  static getOrdersInRange = OrderApiUrls.baseOrderUrl + '/get-in-range'
  static hasBought = OrderApiUrls.baseOrderUrl + '/has-user-bought-item'
}
