import {Component, OnInit} from "@angular/core";
import {Store} from "@ngrx/store";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../service/login.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register-form',
  templateUrl: 'register-form.component.html',
  styleUrls: ['register-form.component.less']
})
export class RegisterFormComponent implements OnInit{

  registerForm: FormGroup

  error:string = ''

  showPassword: boolean = false

  constructor(private loginService: LoginService, public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.registerForm = new FormGroup({
      'username': new FormControl(null, Validators.required),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, Validators.required),
    })
  }

  onSubmit() {
    this.loginService.Register(this.registerForm.get('username')?.value, this.registerForm.get('password')?.value, this.registerForm.get('email')?.value)
      .subscribe({
        next: data => {
          console.log('Success')
          this.snackBar.open('The email containing the activation link was successfully sent', 'X', {
            duration: 6000,
            panelClass: ['snackbar-success']
          })
          this.registerForm.reset()

        },
        error: err => {
          this.error = err.error.message
        }
      })
  }

  onToggle() {
    this.showPassword = !this.showPassword;
  }

}
