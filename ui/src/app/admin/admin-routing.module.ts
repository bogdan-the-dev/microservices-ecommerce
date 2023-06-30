import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import * as path from "path";
import {AdminManagementPage} from "./page/admin-management-page/admin-management.page";
import {PromotionPage} from "./page/promotion/promotion-page/promotion.page";
import {CategoryPage} from "./page/category-page/category.page";
import {ProductManagementPage} from "./page/product-management-page/product-management.page";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'admin', component: AdminManagementPage, children: [
          {path: 'promotions', component: PromotionPage},
          {path: 'categories', component: CategoryPage},
          {path: 'products', component: ProductManagementPage}
        ]}
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class AdminRoutingModule{}
