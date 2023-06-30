import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {RegisterPage} from "./page/register-page/register.page";
import {VerifyAccountPage} from "./page/verify-account-page/verify-account.page";
import {ResetPasswordPage} from "./page/reset-password-page/reset-password.page";
import {ForgotPasswordPage} from "./page/forgot-password-page/forgot-password.page";
import {SendVerificationCodePage} from "./page/send-verification-code-page/send-verification-code.page";
import {LoginPage} from "./page/login-page/login.page";


@NgModule({
  imports: [
    RouterModule.forChild([
      {path: 'register', component: RegisterPage},
      {path: 'verify-account', component: VerifyAccountPage},
      {path: 'reset-password', component: ResetPasswordPage},
      {path: 'forgot-password', component: ForgotPasswordPage},
      {path: 'send-verification-code', component: SendVerificationCodePage},
      {path: 'login', component: LoginPage}
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class LoginRoutingModule{}
