import {Component, Input} from "@angular/core";
import {MyOrderModel} from "../../model/my-order.model";

@Component({
  selector: 'app-my-order-component',
  templateUrl: 'my-order.component.html',
  styleUrls: ['my-order.component.less']
})
export class MyOrderComponent {
  @Input()order: MyOrderModel



}
