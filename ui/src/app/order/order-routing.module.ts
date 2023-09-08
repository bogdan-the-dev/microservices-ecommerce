import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {CheckoutPage} from "./page/checkout-page/checkout.page";
import {AuthGuard} from "../shared/guard/auth.guard";
import {MyOrdersPage} from "./page/my-orders-page/my-orders.page";
import {PaymentFailComponent} from "./component/payment-fail/payment-fail.component";
import {AboutUsPage} from "../shared/page/about-us-page/about-us.page";
import {TermsPage} from "../shared/page/terms-page/terms.page";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'order/checkout', component: CheckoutPage, canActivate: [AuthGuard]},
      {path: 'order/my-orders', component: MyOrdersPage, canActivate: [AuthGuard]},
      {path: 'payment/fail', component: PaymentFailComponent, canActivate: [AuthGuard]},
      {path: 'about-us', component: AboutUsPage},
      {path: 'terms', component: TermsPage}
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class OrderRoutingModule{}
