import {OrderStatus} from "./order-status";

export interface MyOrderModel {
  id: number
  date: Date
  total: number
  status: OrderStatus
  trackingNumber: string
}
