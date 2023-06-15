import {Component, OnInit} from "@angular/core";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../service/login.service";
import {ActivatedRoute} from "@angular/router";
import {take} from "rxjs";

@Component({
  selector: 'app-reset-password.page',
  templateUrl: 'reset-password.page.html',
  styleUrls: ['reset-password.page.less']
})
export class ResetPasswordPage implements OnInit{

  form: FormGroup
  showPassword: boolean = false
  error: string = ''


  private token: string

  constructor(private loginService: LoginService, private snackBar: MatSnackBar, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.form = new FormGroup({
      'password': new FormControl(null, Validators.required),
    })
    this.route.queryParams.pipe(
      take(1)
    ).subscribe(params=> {
      this.token = params["token"]
    })
  }

  onSubmit() {
    this.loginService.ResetPassword(this.token, this.form.get('password')?.value)
      .subscribe({
        next: data => {

        },
        error: err => {
          this.error = err.error.message()
        }
      })
  }

  onToggle() {
    this.showPassword = !this.showPassword;
  }
}
