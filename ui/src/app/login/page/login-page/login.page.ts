import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login.page',
  templateUrl: 'login.page.html',
  styleUrls: ['login.page.less']
})
export class LoginPage implements OnInit{
  loginForm: FormGroup;
  showPassword: boolean = false

  constructor() {}

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      'email': new FormControl('', [Validators.required, Validators.email]),
      'password': new FormControl('', Validators.required)
    });
  }

  login(): void {
    if (this.loginForm.valid) {
      // Perform the login logic
    }
  }

  onToggle() {
    this.showPassword = !this.showPassword;
  }

}
