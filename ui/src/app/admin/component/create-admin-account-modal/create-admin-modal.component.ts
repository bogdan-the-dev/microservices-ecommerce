import {Component, Inject, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AdminAccountGridModel} from "../../model/admin-account-grid.model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LoginService} from "../../../login/service/login.service";

@Component({
  selector: 'app-create-admin-modal',
  templateUrl: 'create-admin-modal.component.html',
  styleUrls: ['create-admin-modal.component.less']
})
export class CreateAdminModalComponent implements OnInit{
  form: FormGroup
  showPassword: boolean = false
  error:string = ''

  constructor(public dialogRef: MatDialogRef<CreateAdminModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data,
              private userService: LoginService
              ){
  }

  ngOnInit() {
    this.form = new FormGroup({
      'username': new FormControl(null, Validators.required),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, Validators.required)
    })
  }

  onSubmit() {
    this.userService.RegisterAdmin(this.form.get('username')?.value, this.form.get('email')?.value, this.form.get('password')?.value).subscribe( {
      next: _ => {
        this.closeDialog()
      },
      error: err => {
        this.error = err.error.message
      }
    })
  }

  onToggle() {
    this.showPassword = !this.showPassword;
  }

  closeDialog(): void {
    this.dialogRef.close()
  }
}
