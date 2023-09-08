import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CheckoutPage} from "./page/checkout-page/checkout.page";
import {OrderRoutingModule} from "./order-routing.module";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatStepperModule} from "@angular/material/stepper";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatRadioModule} from "@angular/material/radio";
import {MyOrdersPage} from "./page/my-orders-page/my-orders.page";
import {MyOrderComponent} from "./component/my-order-component/my-order.component";
import {PaymentFailComponent} from "./component/payment-fail/payment-fail.component";



@NgModule({
  declarations: [
    CheckoutPage,
    MyOrdersPage,
    MyOrderComponent,
    PaymentFailComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatStepperModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCheckboxModule,
    MatRadioModule,

  ]
})
export class OrderModule { }
