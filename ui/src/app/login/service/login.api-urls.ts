import {ApiUrls} from "../../shared/service/api-urls";

export class LoginApiUrls {
  static baseLoginUrl = ApiUrls.serverUrl + '/api/v1/auth'
  static loginUrl = LoginApiUrls.baseLoginUrl + '/authenticate'
  static registerUrl = LoginApiUrls.baseLoginUrl + '/register'
  static verifyAccount = LoginApiUrls.baseLoginUrl + '/verify-account'
  static resendVerificationCode = LoginApiUrls.baseLoginUrl + '/resend-account-verification-code'
  static sendResetPasswordCode = LoginApiUrls.baseLoginUrl + '/send-reset-code'
  static forgotPassword = LoginApiUrls.baseLoginUrl + '/reset-password'
  static recoverState = LoginApiUrls.baseLoginUrl + '/get-user-data'
}
