import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CartComponent} from "./component/cart/cart.component";
import {MatIconModule} from "@angular/material/icon";
import {MatBadgeModule} from "@angular/material/badge";



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
    MatBadgeModule
  ]
})
export class ShoppingCartModule { }
