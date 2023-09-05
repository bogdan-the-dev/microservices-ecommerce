import {OrderStatus} from "./order-status";

export interface EditOrderModel {
  orderId: number
  status: OrderStatus
  trackingNumber: string
}
