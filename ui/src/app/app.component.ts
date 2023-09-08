import {Component, OnInit} from '@angular/core';
import {BaseComponent} from "./shared/component/base-component/base-component";
import {select, Store} from "@ngrx/store";
import {LoginAction} from "./login/state-management/login.action";
import {CartAction} from "./shopping-cart/state-management/cart.action";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent extends BaseComponent implements OnInit{
  title = 'ui';

  constructor(private store: Store<any>) {
    super();
  }

  override ngOnInit() {
    this.store.dispatch({type: CartAction.GET_CART, payload: false})
    if(localStorage.getItem('token') != undefined) {
      this.store.dispatch({type: LoginAction.RECOVER_STATE, payload: localStorage.getItem('token')})
    } else {
    }
  }
}
