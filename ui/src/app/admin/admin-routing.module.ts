import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import * as path from "path";
import {AdminManagementPage} from "./page/admin-management-page/admin-management.page";
import {PromotionPage} from "./page/promotion/promotion-page/promotion.page";
import {CategoryPage} from "./page/category-page/category.page";
import {ProductManagementPage} from "./page/product-management-page/product-management.page";
import {CreateAdminAccountPage} from "./page/create-admin-account/create-admin-account.page";
import {InventoryManagementPage} from "./page/inventory-management-page/inventory-management.page";
import {OrderManagementPage} from "./page/order-management-page/order-management.page";
import {RevenuesPage} from "./page/revenues/revenues.page";
import {AuthAdminGuard} from "../shared/guard/auth-admin.guard";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'admin', component: AdminManagementPage, children: [
          {path: 'promotions', component: PromotionPage, canActivate: [AuthAdminGuard]},
          {path: 'categories', component: CategoryPage, canActivate: [AuthAdminGuard]},
          {path: 'products', component: ProductManagementPage, canActivate: [AuthAdminGuard]},
          {path: 'create-admin', component: CreateAdminAccountPage, canActivate: [AuthAdminGuard]},
          {path: 'inventory', component: InventoryManagementPage, canActivate: [AuthAdminGuard]},
          {path: 'order-management', component: OrderManagementPage, canActivate: [AuthAdminGuard]},
        ], canActivate: [AuthAdminGuard]}
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class AdminRoutingModule{}
