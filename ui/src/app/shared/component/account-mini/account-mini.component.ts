import {Component} from "@angular/core";
import {BaseComponent} from "../base-component/base-component";
import {select, Store} from "@ngrx/store";
import {filter} from "rxjs";
import {LoginAction} from "../../../login/state-management/login.action";

@Component({
  selector: 'app-account-mini',
  templateUrl: 'account-mini.component.html',
  styleUrls: ['account-mini.component.less']
})
export class AccountMiniComponent extends BaseComponent{
  mainButtonText: string;
  isAuthenticated

  constructor(private store: Store<any>) {
    super();
  }

  override ngOnInit() {
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.loginModuleFeature.loginState),
        filter(s => s !== undefined)
      ).subscribe(loginData => {
        if(loginData.success) {
          this.mainButtonText = 'Hi, ' + loginData.username
          this.isAuthenticated = true
        }
        else {
          this.mainButtonText = 'Account'
          this.isAuthenticated = false
        }
      })
    )
  }

  onLogout() {
    this.store.dispatch({type: LoginAction.LOGOUT, payload: {}})
  }

}
