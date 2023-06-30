import {Component, Inject, Input, OnInit} from "@angular/core";
import {PromotionModel} from "../../model/promotion.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PromotionService} from "../../service/promotion.service";

@Component({
  selector: 'app-promotion-modal',
  templateUrl: 'promotion-modal.component.html',
  styleUrls: ['promotion-modal.component.less']
})
export class PromotionModalComponent implements OnInit{
  form: FormGroup
  @Input() model: PromotionModel;
  @Input() editMode: boolean;

  buttonAction: string
  constructor(public dialogRef: MatDialogRef<PromotionModalComponent>,@Inject(MAT_DIALOG_DATA) public data: any, private promotionService: PromotionService) {
    this.model = data.model;
    this.editMode = data.editMode;
  }


  ngOnInit() {
    if (this.editMode) {
      // Perform any additional initialization for edit mode
      this.buttonAction = 'Edit'
    } else {
      // Perform initialization for create mode
      this.model = {
        id: '',
        name: '',
        percentage: 0,
        active: false,
        creationTimestamp: new Date()
      };
      this.buttonAction = 'Create'
    }
    this.form = new FormGroup({
      'name': new FormControl(this.model.name, Validators.required),
      'percentage': new FormControl(this.model.percentage, [Validators.required, this.percentageValidator]),
    })
  }

  onSubmit() {
    // Perform any necessary logic with the model object
    console.log(this.model);
    if(this.editMode) {
      this.model.name = this.form.get('name').value
      this.model.percentage = this.form.get('percentage').value
      this.promotionService.editPromotion(this.model).subscribe(_ => {
        this.closeDialog()
      })
    } else {
      const promo: PromotionModel ={id: null, name: this.form.get('name').value, active: true, percentage: this.form.get('percentage').value, creationTimestamp: null}
      this.promotionService.createPromotion(promo).subscribe(res => {
        this.closeDialog()
      })
    }

    // Close the popup or reset the form
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  percentageValidator(control) {
    const percentage = +control.value;
    if (isNaN(percentage) || percentage >= 100 || percentage <= 0) {
      return { invalidPercentage: true };
    }
    return null;
  }

}
