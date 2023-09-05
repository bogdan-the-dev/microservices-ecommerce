import {Component, Inject, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ReviewService} from "../../service/review.service";

@Component({
  selector: 'app-review-modal',
  templateUrl: 'review.modal.componenet.html',
  styleUrls: ['review.modal.componenet.less']
})
export class ReviewModalComponenet implements OnInit{
  form: FormGroup

  constructor(public dialogRef: MatDialogRef<ReviewModalComponenet>,
              @Inject(MAT_DIALOG_DATA) public data,
              private reviewService: ReviewService) {
  }

  ngOnInit() {
    this.form = new FormGroup({
      'rating' : new FormControl('', [Validators.required, Validators.min(1), Validators.max(5)]),
      'review' : new FormControl('', Validators.required)
    })
  }

  onAdd() {
    this.reviewService.addReview({productId: this.data.id, rating: this.form.get('rating').value, review: this.form.get('review').value}).subscribe(_ => {
      this.closeDialog()
    })
  }

  closeDialog(): void {
    this.dialogRef.close()
  }


}
