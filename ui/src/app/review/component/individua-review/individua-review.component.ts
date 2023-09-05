import {Component, Input} from "@angular/core";
import {IndividualReviewModel} from "../../model/individual.review.model";

@Component({
  selector: 'app-individual-review-component',
  templateUrl: 'individua-review.component.html',
  styleUrls: ['individua-review.component.less']
})
export class IndividuaReviewComponent {
  @Input() review: IndividualReviewModel


}
