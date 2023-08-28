import {Component, Inject, OnInit} from "@angular/core";
import {Form, FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LoginService} from "../../../login/service/login.service";
import {InventoryService} from "../../service/inventory.service";

@Component({
  selector: 'app-edit-inventory-modal',
  templateUrl: 'edit-Inventory-modal.component.html',
  styleUrls: ['edit-Inventory-modal.component.less']
})
export class EditInventoryModalComponent implements OnInit{
  form: FormGroup

  constructor(public dialogRef: MatDialogRef<EditInventoryModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data,
              private inventoryService: InventoryService) {
  }

  ngOnInit() {
    this.form = new FormGroup({
      'quantity': new FormControl(this.data.quantity, [Validators.required, Validators.min(0)])
    })
  }

  onSubmit() {
    this.inventoryService.EditInventory(this.data.productId, this.form.get('quantity').value).subscribe( _ => {
      this.closeDialog()
    })
  }

  closeDialog(): void {
    this.dialogRef.close()
  }
}
