import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-delete-modal',
  templateUrl: 'confirm-delete-modal.component.html',
  styleUrls: ['confirm-delete-modal.component.less']
})
export class ConfirmDeleteModalComponent {
  name: string
  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ){
    this.name = data
  }

  confirm() {
    this.dialogRef.close('confirm')
  }
}
