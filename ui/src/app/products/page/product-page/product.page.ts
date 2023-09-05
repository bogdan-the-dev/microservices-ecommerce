import {Component, OnInit} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {ActivatedRoute} from "@angular/router";
import {ReviewService} from "../../../review/service/review.service";
import {ReviewsModel} from "../../../review/model/reviews.model";
import {select, Store} from "@ngrx/store";
import {filter} from "rxjs";
import {OrderService} from "../../../order/service/order.service";
import {CategoryModalComponent} from "../../../admin/component/category-modal/category-modal.component";
import {MatDialog} from "@angular/material/dialog";
import {ReviewModalComponenet} from "../../../review/component/review-modal/review.modal.componenet";

@Component({
  selector: 'app-product.page',
  templateUrl: 'product.page.html',
  styleUrls: ['product.page.less']
})
export class ProductPage extends BaseComponent implements OnInit{

  productId: string

  reviews: ReviewsModel
  private username: string = ''
  private postedReview: boolean = true
  private hasBought: boolean = false
  constructor(private dialog: MatDialog, private route: ActivatedRoute, private reviewService: ReviewService, private store: Store<any>, private orderService: OrderService) {
    super();

  }

  override ngOnInit() {
    this.subscriptions.push(this.route.params.subscribe(params => {
      this.productId = params['id']
      this.reviewService.getReviews(this.productId).subscribe(res => {
        this.reviews = res;
      })
        this.reviewService.getReviewPresent(this.productId).subscribe(res => {
          this.postedReview = res
        },
          error => {
          this.postedReview = true
          }

        )
        this.orderService.getHasBoughtItem(this.productId).subscribe(res => {
          this.hasBought = res
        },
          error => {
          this.hasBought = false
          }
          )
    }))
  }


  canAddReview(): boolean {
    return this.hasBought && !this.postedReview
  }

  onAdd() {
    const elementRef = this.dialog.open(ReviewModalComponenet, {
      width: '400px',
      data: {
        id: this.productId
      }
    })
  }

}
