import {Component, OnInit} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {select, Store} from "@ngrx/store";
import {LoginAction} from "../../state-management/login.action";
import {filter} from "rxjs";

@Component({
  selector: 'app-login-form',
  templateUrl: 'login-form.component.html',
  styleUrls: ['login-form.component.less']
})
export class LoginFormComponent extends BaseComponent implements OnInit{
  loginForm: FormGroup

  showPassword: boolean = false

  errorMessage: ''
  error: boolean;

  constructor(private store: Store<any>) {
    super();
  }

  override ngOnInit() {
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.loginModuleFeature.loginState.errorMessage),
        filter(s => s !== undefined)
      ).subscribe(error => {
        this.errorMessage = error;
      })
    )
    this.initForm();
  }

  private initForm() {
    this.loginForm = new FormGroup({
      'username': new FormControl(null, Validators.required),
      'password': new FormControl(null, Validators.required)
    })
  }

  onSubmit() {
    const payload = {username: this.loginForm.get('username')?.value, password: this.loginForm.get('password')?.value}
    console.log(payload)
    this.store.dispatch({type: LoginAction.LOGIN, payload: payload})
  }

  onToggle() {
    this.showPassword = !this.showPassword;
  }

}
