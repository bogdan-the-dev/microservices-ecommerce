import {HttpClient} from "@angular/common/http";
import {OrderModule} from "../order.module";
import {OrderModel} from "../model/order.model";
import {OrderApiUrls} from "./order.api-urls";
import {CreateOrderModel} from "../model/create-order.model";
import {Injectable} from "@angular/core";
import {MyOrderModel} from "../model/my-order.model";
import {EditOrderModel} from "../model/edit-order.model";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  constructor(private http: HttpClient) {
  }

  place(order: CreateOrderModel) {
    return this.http.post(OrderApiUrls.placeOrder, order)
  }

  getMyOrders(username) {
    return this.http.get<MyOrderModel[]>(OrderApiUrls.getForUser + '?username=' + username)
  }

  getAll() {
    return this.http.get<MyOrderModel[]>(OrderApiUrls.getAll)
  }

  getHasBoughtItem(itemId: string) {
    return this.http.get<boolean>(OrderApiUrls.hasBought + '?itemId='+itemId)
  }

  cancelOrder(orderId) {
    return this.http.put(OrderApiUrls.cancel, orderId)
  }

  editOrder(data: EditOrderModel) {
    return this.http.put(OrderApiUrls.edit, data)
  }

}
