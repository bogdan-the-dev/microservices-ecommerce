import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ApiUrls} from "../../shared/service/api-urls";
import {AddReviewModel} from "../model/add-review.model";
import {ReviewsModel} from "../model/reviews.model";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  static BASE_URL = ApiUrls.serverUrl + '/api/v1/reviews'
  static GET_REVIEWS = ReviewService.BASE_URL + '/get-reviews'
  static ADD_REVIEW = ReviewService.BASE_URL + '/add-review'
  static GET_REVIEW_PRESENT = ReviewService.BASE_URL + '/verify-review-present'
  constructor(private http: HttpClient) {
  }

  getReviews(productId: string) {
    return this.http.get<ReviewsModel>(ReviewService.GET_REVIEWS + '?id=' + productId)
  }

  addReview(review: AddReviewModel) {
    return this.http.put(ReviewService.ADD_REVIEW, review)
  }

  getReviewPresent(productId: string) {
    return this.http.get<boolean>(ReviewService.GET_REVIEW_PRESENT+'?productID='+productId)
  }

}
