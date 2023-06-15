import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductOverviewComponent} from "./component/product-overview.component";
import {ProductsPage} from "./page/products-page/products.page";
import {ProductsRoutingModule} from "./products-routing.module";
import {MatIconModule} from "@angular/material/icon";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";



@NgModule({
  declarations: [
    ProductOverviewComponent,
    ProductsPage
  ],
  imports: [
    CommonModule,
    ProductsRoutingModule,
    MatIconModule,
    NgbModule
  ]
})
export class ProductsModule { }
