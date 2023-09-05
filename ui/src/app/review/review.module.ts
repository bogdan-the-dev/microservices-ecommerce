import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IndividuaReviewComponent} from "./component/individua-review/individua-review.component";
import {MatCardModule} from "@angular/material/card";
import {ReviewModalComponenet} from "./component/review-modal/review.modal.componenet";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatTooltipModule} from "@angular/material/tooltip";
import {ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    IndividuaReviewComponent,
    ReviewModalComponenet
  ],
  exports: [
    IndividuaReviewComponent
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatTooltipModule,
    ReactiveFormsModule
  ]
})
export class ReviewModule { }
