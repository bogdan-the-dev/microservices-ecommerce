import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {filter, Observable} from "rxjs";
import {select, Store} from "@ngrx/store";
import {LoginModuleState} from "../login/state-management/login.state";

@Injectable()
export class AddHeaderInterceptor  implements HttpInterceptor {
  private token: string = ''

  constructor(store: Store<any>) {
    store.pipe(
      select(s => s.loginModuleFeature.loginState.token),
      filter(s => s !== null)
    ).subscribe(token => {
      this.token = token
    })
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(this.token == undefined || this.token == '') {
      return next.handle(req)
    }
    const clone = req.clone({headers: req.headers.append('Authorization', 'Bearer ' + this.token)})
    return next.handle(clone);
  }


}
