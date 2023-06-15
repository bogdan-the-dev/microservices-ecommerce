import {Component, OnInit} from "@angular/core";
import {LoginService} from "../../service/login.service";
import {ActivatedRoute} from "@angular/router";
import {take} from "rxjs";

@Component({
  selector: 'app-verify-account.page',
  templateUrl: 'verify-account.page.html',
  styleUrls: ['verify-account.page.less']
})
export class VerifyAccountPage implements OnInit{
  errorMsg: string = ''
  code: string
  success: boolean
  constructor(private loginService: LoginService, private rote: ActivatedRoute) {
  }

  ngOnInit() {
    this.rote.queryParams.pipe(
      take(1)
    ).subscribe(params => {
      this.code = params['token']
      this.loginService.VerifyAccount(this.code).subscribe({
        next: data => {
          this.success = true
        },
        error: err => {
          this.errorMsg = err.error.message()
          this.success = false
        }
      })
    })
  }

}
