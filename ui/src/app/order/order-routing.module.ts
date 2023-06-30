import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {CheckoutPage} from "./page/checkout-page/checkout.page";
import {AuthGuard} from "../shared/guard/auth.guard";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'order/checkout', component: CheckoutPage, canActivate: [AuthGuard]}
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class OrderRoutingModule{}
