import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {AuthRedirectService} from "../service/auth-redirect.service";
import {select, Store} from "@ngrx/store";
import {filter, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthAdminGuard implements CanActivate {
  userRole: string = ''
  constructor(private router: Router, private store: Store<any>) {
    this.store.pipe(
      select(s => s.loginModuleFeature.loginState.role),
      filter(s => s !== undefined)
    ).subscribe(role => {
      this.userRole = role
    })
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.userRole == "ADMIN"

  }

}
