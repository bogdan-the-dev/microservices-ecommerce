import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginFormComponent} from "./component/login-form/login-form.component";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {ReactiveFormsModule} from "@angular/forms";
import {StoreModule} from "@ngrx/store";
import {loginReducersMap} from "./state-management/login.state";
import {loginFeatureName} from "./state-management/login.state"
import {EffectsModule} from "@ngrx/effects";
import {LoginEffect} from "./state-management/login.effect";
import {MatIconModule} from "@angular/material/icon";
import {RegisterPage} from "./page/register-page/register.page";
import {LoginRoutingModule} from "./login-routing.module";
import {RegisterFormComponent} from "./component/register-form/register-form.component";
import {ForgotPasswordPage} from "./page/forgot-password-page/forgot-password.page";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {ResetPasswordPage} from "./page/reset-password-page/reset-password.page";
import {VerifyAccountPage} from "./page/verify-account-page/verify-account.page";
import {SendVerificationCodePage} from "./page/send-verification-code-page/send-verification-code.page";
import {LoginPage} from "./page/login-page/login.page";


@NgModule({
  declarations: [
    LoginFormComponent,
    RegisterPage,
    RegisterFormComponent,
    ForgotPasswordPage,
    ResetPasswordPage,
    VerifyAccountPage,
    SendVerificationCodePage,
    LoginPage
  ],
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    StoreModule.forFeature(loginFeatureName, loginReducersMap),
    EffectsModule.forFeature([LoginEffect]),
    MatIconModule,
    LoginRoutingModule,
    MatSnackBarModule
  ],
  exports: [
    LoginFormComponent
  ]
})
export class LoginModule { }
