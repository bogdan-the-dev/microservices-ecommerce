import {Component, OnInit} from "@angular/core";
import {MyOrderModel} from "../../model/my-order.model";
import {OrderService} from "../../service/order.service";
import {select, Store} from "@ngrx/store";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {filter} from "rxjs";

@Component({
  selector: 'app-my-orders-page',
  templateUrl: 'my-orders.page.html',
  styleUrls: ['my-orders.page.less']
})
export class MyOrdersPage extends BaseComponent implements OnInit{
  myOrders: MyOrderModel[] = []

  constructor(private service: OrderService, private store: Store<any>) {
    super();
  }

  override ngOnInit() {
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.loginModuleFeature.loginState),
        filter(s => s !== undefined)
      ).subscribe(loginData => {
        if(loginData.success) {
          this.service.getMyOrders(loginData.username).subscribe(res => {
            this.myOrders = res
          })
        }

      })
    )
  }
}
