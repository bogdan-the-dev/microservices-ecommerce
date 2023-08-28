import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {LoginApiUrls} from "./login.api-urls";
import {LoginAction} from "../state-management/login.action";
import {AdminAccountGridModel} from "../../admin/model/admin-account-grid.model";


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http: HttpClient) {
  }


  Login(username: string, password: string) {
    return this.http.post(LoginApiUrls.loginUrl, {username: username, password: password})
  }
  Register(username: string, password: string, email: string) {
    return this.http.post(LoginApiUrls.registerUrl, {username: username, email: email, password: password})
  }
  VerifyAccount(token: string) {
    return this.http.get(LoginApiUrls.verifyAccount + '?token=' + token)
  }
  ResendAccountVerificationCode(email: string) {
    return this.http.get(LoginApiUrls.resendVerificationCode + '?email=' + email)
  }
  SendResetCode(email: string) {
    return this.http.get(LoginApiUrls.sendResetPasswordCode + '?email=' + email)
  }
  ResetPassword(token: string, password: string) {
    return this.http.put(LoginApiUrls.forgotPassword + '?token=' + token, {password: password})
  }
  RecoverState(token: string) {
    return this.http.get(LoginApiUrls.recoverState + '?token=' + token)
  }

  RegisterAdmin(username: string, email: string, password: string) {
    return this.http.post(LoginApiUrls.registerAdmin, {username: username, email: email, password: password})
  }

  EnableAdmin(username: string[]) {
    return this.http.put(LoginApiUrls.enableAdmin, username)
  }

  DisableAdmin(username: string[]) {
    return this.http.put(LoginApiUrls.disableAdmin, username)
  }

  DeleteAdmin(username: string[]) {
    return this.http.put(LoginApiUrls.deleteAdmin, username)
  }
  GetAdmins() {
    return this.http.get<AdminAccountGridModel[]>(LoginApiUrls.getAdmin)
  }
}
