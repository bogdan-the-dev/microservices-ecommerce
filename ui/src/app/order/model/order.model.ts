import {PaymentType} from "./payment-type";
import {OrderStatus} from "./order-status";

export interface OrderModel {
  id: string
  username: string
  orderDate: Date
  orderTotal: number
  trackingNumber: string
  phoneNumber: string
  billingAddress: string
  notifySMS: boolean
  deliveryAddress: string
  billingName: string
  deliveryName: string
  transportCost: number
  stripeId: string
  paymentType: PaymentType
  status: OrderStatus
}
