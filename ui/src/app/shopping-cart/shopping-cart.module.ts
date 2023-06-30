import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CartComponent} from "./component/cart/cart.component";
import {MatIconModule} from "@angular/material/icon";
import {MatBadgeModule} from "@angular/material/badge";
import {StoreModule} from "@ngrx/store";
import {cartFeatureName, cartReducerMap} from "./state-management/cart.state";
import {EffectsModule} from "@ngrx/effects";
import {CartEffect} from "./state-management/cart.effect";
import {ProductsService} from "../products/service/products.service";
import {NgbDropdownModule} from "@ng-bootstrap/ng-bootstrap";
import {ShoppingCartRoutingModule} from "./shopping-cart-routing.module";
import {OrderRoutingModule} from "../order/order-routing.module";



@NgModule({
  declarations: [
    CartComponent,
  ],
  exports: [
    CartComponent
  ],
  imports: [
    CommonModule,
    MatIconModule,
    MatBadgeModule,
    StoreModule.forFeature(cartFeatureName, cartReducerMap),
    EffectsModule.forFeature([CartEffect]),
    NgbDropdownModule,
    ShoppingCartRoutingModule,
    OrderRoutingModule
  ],
  providers: [
    ProductsService
  ]
})
export class ShoppingCartModule { }
