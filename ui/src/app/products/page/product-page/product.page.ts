import {Component, OnInit} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-product.page',
  templateUrl: 'product.page.html',
  styleUrls: ['product.page.less']
})
export class ProductPage extends BaseComponent implements OnInit{

  productId: string

  constructor(private route: ActivatedRoute) {
    super();

  }

  override ngOnInit() {
    this.subscriptions.push(this.route.params.subscribe(params => {
      this.productId = params['id']
    }))
  }


}
