import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {HeaderComponent} from "./component/header/header.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {CartComponent} from "../shopping-cart/component/cart/cart.component";
import {BaseComponent} from "./component/base-component/base-component";
import {MatBadgeModule} from "@angular/material/badge";
import {AccountMiniComponent} from "./component/account-mini/account-mini.component";
import {DropdownDirective} from "./directive/dropdown.directive";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {LoginModule} from "../login/login.module";
import {ShoppingCartModule} from "../shopping-cart/shopping-cart.module";
import {MatListModule} from "@angular/material/list";
import {RouterLink, RouterLinkActive, RouterLinkWithHref} from "@angular/router";
import {NavbarComponent} from "./component/navbar/navbar.component";
import {ProductsModule} from "../products/products.module";
import {MatMenuModule} from "@angular/material/menu";
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    HeaderComponent,
    BaseComponent,
    AccountMiniComponent,
    DropdownDirective,
    NavbarComponent
  ],
  exports: [
    HeaderComponent,
    NavbarComponent
  ],
    imports: [
        CommonModule,
        MatToolbarModule,
        NgOptimizedImage,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatBadgeModule,
        NgbModule,
        LoginModule,
        ShoppingCartModule,
        MatListModule,
        RouterLinkWithHref,
        RouterLink,
        RouterLinkActive,
        ProductsModule,
        MatMenuModule,
        FormsModule
    ]
})
export class SharedModule { }
