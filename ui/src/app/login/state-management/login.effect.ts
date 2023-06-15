import {Injectable} from "@angular/core";
import {LoginService} from "../service/login.service";
import {Actions, Effect, ofType} from '@ngrx/effects';
import {LoginAction} from "./login.action";
import {Convert} from "../../shared/utils/convert";
import {catchError, map, of, switchMap} from "rxjs";

@Injectable()
export class LoginEffect {
  constructor(private actions: Actions, private loginService: LoginService) {
  }

  @Effect()
  login = this.actions.pipe(
    ofType(LoginAction.LOGIN),
    map(Convert.ToPayload),
    switchMap(payload => {
      return this.loginService.Login(payload.username, payload.password)
        .pipe(
          switchMap(result => {
            return of({type: LoginAction.LOGIN_FINISHED, payload: {success: true, data:result}})
          }),
          catchError(error => {
            return of({type: LoginAction.LOGIN_ERROR, payload: {success: false, data: error}})
          })
        )
    })
  )

  @Effect()
  recover = this.actions.pipe(
    ofType(LoginAction.RECOVER_STATE),
    map(Convert.ToPayload),
    switchMap(payload => {
      return this.loginService.RecoverState(payload)
        .pipe(
          switchMap(result => {
            return of({type: LoginAction.RECOVER_STATE_FINISHED, payload: {data: result}})
          }),
          catchError(error => {
            return of({type: LoginAction.LOGIN_ERROR, payload: {success: false, data: error}})
          })
        )
    })
  )
}
