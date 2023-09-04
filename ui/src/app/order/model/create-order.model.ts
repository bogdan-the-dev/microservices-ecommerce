import {PaymentType} from "./payment-type";
import {OrderStatus} from "./order-status";
import {OrderModel} from "./order.model";
import {OrderItemDtoModel} from "./order-item-dto.model";

export interface CreateOrderModel {

  order: OrderModel,
  orderItemDTOList: OrderItemDtoModel[]
}
