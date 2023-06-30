import {Component, Input} from "@angular/core";
import {ProductPreviewModel} from "../../model/product-preview.model";
import {ItemCartModel} from "../../../shopping-cart/model/item-cart.model";
import {CartAction} from "../../../shopping-cart/state-management/cart.action";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-product-overview',
  templateUrl: 'product-overview.component.html',
  styleUrls: ['product-overview.component.less']
})
export class ProductOverviewComponent {
  @Input() product: ProductPreviewModel

  constructor(private store: Store<any>) {
  }
  get promoLabel(): string {
    return this.product.promo?.name
  }


  getStarWidth(rating: number): string {
    const starWidth = (rating / 5) * 100; // Calculate the width percentage based on rating
    return `${starWidth}%`;
  }

  getLink() {
    return this.product.title.replace(/ /g, '-').replace(/[!@#$%^&*()=_+[\]{};\':"\\\\|,.<>\\/?~`]/g, '')
  }

  onAddToCart() {
    const realPrice = this.product.promActive ? Number((this.product.price - (this.product.price * (this.product.promo.percentage / 100))).toFixed(2)) : this.product.price
    const item: ItemCartModel = {id: this.product.id, price: realPrice, title: this.product.title, quantity: 1, image: this.product.imagePath}
    this.store.dispatch({type: CartAction.ADD_TO_CART, payload: item})

  }

}
