import {Component, OnInit} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";

@Component({
  selector: 'app-cart-mini',
  templateUrl: 'cart.component.html',
  styleUrls: ['cart.component.less']
})
export class CartComponent extends BaseComponent implements OnInit {
  count: number = 0;



  override ngOnInit() {
    super.ngOnInit();
  }

}
