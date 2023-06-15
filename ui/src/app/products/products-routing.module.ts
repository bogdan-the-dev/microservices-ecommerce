import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {ProductsPage} from "./page/products-page/products.page";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'products', component: ProductsPage}
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class ProductsRoutingModule {}
