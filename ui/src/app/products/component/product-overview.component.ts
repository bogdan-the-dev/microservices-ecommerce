import {Component, Input} from "@angular/core";
import {ProductPreviewModel} from "../model/product-preview.model";

@Component({
  selector: 'app-product-overview',
  templateUrl: 'product-overview.component.html',
  styleUrls: ['product-overview.component.less']
})
export class ProductOverviewComponent {
  @Input() product: ProductPreviewModel

  get promoLabel(): string {
    return this.product.promo?.name
  }

  protected readonly Math = Math;

  getStarWidth(rating: number): string {
    const starWidth = (rating / 5) * 100; // Calculate the width percentage based on rating
    return `${starWidth}%`;
  }

}
