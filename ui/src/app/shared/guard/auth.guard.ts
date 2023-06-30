import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {AuthRedirectService} from "../service/auth-redirect.service";
import {select, Store} from "@ngrx/store";
import {filter, Observable, take} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class AuthGuard implements CanActivate {
  isAuthenticated: boolean = false
  constructor(private router: Router, private authRedirectService: AuthRedirectService, private store: Store<any>) {
    this.store.pipe(
      select(s => s.loginModuleFeature.loginState.success),
      filter(s => s !== undefined)
    ).subscribe(isAuthenticated => {
      this.isAuthenticated = isAuthenticated
    })
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.isAuthenticated) {
      return true;
    } else {
      this.authRedirectService.setRedirectUrl(state.url)
      this.router.navigate(['/login'])
      return false;
    }
  }


}
