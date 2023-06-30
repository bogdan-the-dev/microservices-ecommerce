import {Component, OnInit} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {select, Store} from "@ngrx/store";
import {LoginAction} from "../../state-management/login.action";
import {filter} from "rxjs";
import {AuthRedirectService} from "../../../shared/service/auth-redirect.service";
import {Router} from "@angular/router";

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

  constructor(private store: Store<any>, private authRedirectService: AuthRedirectService, private router: Router) {
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
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.loginModuleFeature.loginState.success),
        filter(s => s !== undefined)
      ).subscribe(loginSuccess => {
        if(loginSuccess) {
          const redirectUrl = this.authRedirectService.getRedirectUrl()
          if(redirectUrl != undefined || redirectUrl != null || redirectUrl !== '') {
            this.navigate(redirectUrl)
          }
          else {
            this.navigate('')
          }
        }
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

  private navigate(path: string) {
    this.router.navigate([path])
  }

}
