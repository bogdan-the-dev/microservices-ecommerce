import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {CheckoutPage} from "./page/checkout-page/checkout.page";
import {AuthGuard} from "../shared/guard/auth.guard";
import {MyOrdersPage} from "./page/my-orders-page/my-orders.page";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'order/checkout', component: CheckoutPage, canActivate: [AuthGuard]},
      {path: 'order/my-orders', component: MyOrdersPage, canActivate: [AuthGuard]}
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class OrderRoutingModule{}
