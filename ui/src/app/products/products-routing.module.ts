import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {ProductsPage} from "./page/products-page/products.page";
import {ProductPage} from "./page/product-page/product.page";

@NgModule({
  imports: [
    RouterModule.forChild([
      { path: 'products', component: ProductsPage },
      { path: 'products/:category', component: ProductsPage },
      { path: 'products/:category/:subcategory', component: ProductsPage },
      { path: 'product/:name/:id', component: ProductPage}

    ])
  ],
  exports: [
    RouterModule
  ]
})
export class ProductsRoutingModule {}
