import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductOverviewComponent} from "./component/product-overview/product-overview.component";
import {ProductsPage} from "./page/products-page/products.page";
import {ProductsRoutingModule} from "./products-routing.module";
import {MatIconModule} from "@angular/material/icon";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ProductFilterComponent} from "./component/product-filter/product-filter.component";
import {FormsModule} from "@angular/forms";
import {PriceRangeSliderComponent} from "./component/price-range-slider/price-range-slider.component";
import {MatSliderModule} from "@angular/material/slider";
import {HammerModule} from "@angular/platform-browser";
import {NgxSliderModule} from "@angular-slider/ngx-slider";
import {ProductComponent} from "./component/product/product.component";
import {ProductPage} from "./page/product-page/product.page";
import {MatChipsModule} from "@angular/material/chips";
import {StoreModule} from "@ngrx/store";
import {cartFeatureName, cartReducerMap} from "../shopping-cart/state-management/cart.state";
import {EffectsModule} from "@ngrx/effects";
import {CartEffect} from "../shopping-cart/state-management/cart.effect";
import {MatCheckboxModule} from "@angular/material/checkbox";



@NgModule({
  declarations: [
    ProductOverviewComponent,
    ProductsPage,
    ProductFilterComponent,
    PriceRangeSliderComponent,
    ProductComponent,
    ProductPage,
    ProductComponent
  ],
    imports: [
        CommonModule,
        ProductsRoutingModule,
        MatIconModule,
        NgbModule,
        FormsModule,
        MatSliderModule,
        HammerModule,
        NgxSliderModule,
        MatChipsModule,
        StoreModule.forFeature(cartFeatureName, cartReducerMap),
        EffectsModule.forFeature([CartEffect]),
        MatCheckboxModule
    ]
})
export class ProductsModule { }
