package ro.bogdansoftware.clients.review;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        value = "review",
        path = "api/v1/reviews"
)
public interface IReviewClient {
    @PostMapping("create-reviews")
    ResponseEntity<Void> createReviews(@RequestParam(name = "productId") String productId);

    @DeleteMapping("delete-reviews")
    ResponseEntity<Void> deleteReviews(@RequestParam(name = "productID") String productId);

    @PostMapping("get-rating-for-products")
    ResponseEntity<ReviewProductPreview> getRatingForProducts(@RequestBody List<String> productIds);
}
