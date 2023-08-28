import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AdminRoutingModule} from "./admin-routing.module";
import {AdminManagementPage} from "./page/admin-management-page/admin-management.page";
import {PromotionPage} from "./page/promotion/promotion-page/promotion.page";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {PromotionModalComponent} from "./component/promoton-modal/promotion-modal.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatSortModule} from "@angular/material/sort";
import {MatTooltipModule} from "@angular/material/tooltip";
import {CategoryPage} from "./page/category-page/category.page";
import {
  ConfirmDeleteModalComponent
} from "./component/confirm-delete-modal/confirm-delete-modal.component";
import {MatDialogModule} from "@angular/material/dialog";
import {CategoryModalComponent} from "./component/category-modal/category-modal.component";
import {MatCardModule} from "@angular/material/card";
import {ProductManagementPage} from "./page/product-management-page/product-management.page";
import {ProductModalComponent} from "./component/product-modal/product-modal.component";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {
  ProductSetCategoryModalComponent
} from "./component/product-set-category-modal/product-set-category-modal.component";
import {CreateAdminAccountPage} from "./page/create-admin-account/create-admin-account.page";
import {InventoryManagementPage} from "./page/inventory-management-page/inventory-management.page";
import {OrderManagementPage} from "./page/order-management-page/order-management.page";
import {RevenuesPage} from "./page/revenues/revenues.page";
import {CreateAdminModalComponent} from "./component/create-admin-account-modal/create-admin-modal.component";
import {EditInventoryModalComponent} from "./component/edit-Inventory-modal/edit-Inventory-modal.component";



@NgModule({
  declarations: [
    ConfirmDeleteModalComponent,
    AdminManagementPage,
    PromotionPage,
    PromotionModalComponent,
    CategoryPage,
    CategoryModalComponent,
    ProductManagementPage,
    ProductModalComponent,
    ProductSetCategoryModalComponent,
    CreateAdminAccountPage,
    InventoryManagementPage,
    OrderManagementPage,
    RevenuesPage,
    CreateAdminModalComponent,
    EditInventoryModalComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatIconModule,
    MatButtonModule,
    MatListModule,
    MatTableModule,
    MatCheckboxModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSortModule,
    MatTooltipModule,
    MatDialogModule,
    MatCardModule,
    MatOptionModule,
    MatSelectModule,
    DragDropModule,

  ]
})
export class AdminModule { }
