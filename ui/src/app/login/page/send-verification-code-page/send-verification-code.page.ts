import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../service/login.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-send-verification-code.page',
  templateUrl: 'send-verification-code.page.html',
  styleUrls: ['send-verification-code.page.less']
})
export class SendVerificationCodePage implements OnInit{
  form: FormGroup
  errorMsg = ''
  finished: boolean = false

  constructor(private loginService: LoginService, public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.form = new FormGroup({
      'email': new FormControl('', [Validators.required, Validators.email])
    })
  }

  onSubmit() {
    const email = this.form.get('email')?.value
    this.loginService.SendResetCode(email).subscribe({
      next: data => {
        this.finished = true
        console.log("reset email sent")
        this.snackBar.open('The email containing the password reset link was successfully sent', 'Close', {
          duration: 6000,
          panelClass: ['snackbar-success']
        })
        this.form.reset()
      },
      error: err => {
        this.errorMsg = err.error.message
      }
    })
  }

}
