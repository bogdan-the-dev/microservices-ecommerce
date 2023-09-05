import {Component, Inject, OnInit} from "@angular/core";
import {OrderService} from "../../../order/service/order.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {OrderStatus} from "../../../order/model/order-status";

@Component({
  selector: 'app-edit-order-modal',
  templateUrl: 'order-modal.component.html',
  styleUrls: ['order-modal.component.less']
})
export class OrderModalComponent implements OnInit{
  form: FormGroup
  orderStatusKeys = Object.keys(OrderStatus).filter(key => !isNaN(Number(OrderStatus[key])));

  currentStatus: OrderStatus

  needTracking: boolean = false

  constructor(public dialogRef: MatDialogRef<OrderModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data,private service: OrderService) {
  }

  ngOnInit() {
    this.currentStatus = this.data.status
    this.form = new FormGroup({
      'status': new FormControl(this.data.status, Validators.required),
      'trackingNumber': new FormControl(this.data.tracking)
    })
  }

  onStatusChange(status) {
    console.log(status)
    this.currentStatus = status.value
    if(status.value == "PREPARATION") {
      this.form.get('trackingNumber').addValidators(Validators.required)
      this.form.updateValueAndValidity({emitEvent: true})
      this.needTracking = true
    } else {
      this.form.patchValue(this.form.get('trackingNumber'), this.data.tracking)
      this.form.get('trackingNumber').clearValidators()
      this.form.updateValueAndValidity({emitEvent: true})
      this.needTracking = false
    }
  }

  onSubmit() {
    this.service.editOrder({orderId: this.data.id, status: this.currentStatus, trackingNumber: this.form.get('trackingNumber').value}).subscribe(_ => {
      this.closeDialog()
    })
  }

  closeDialog(): void {
    this.dialogRef.close()
  }

  protected readonly OrderStatus = OrderStatus;
  protected readonly Object = Object;
}
