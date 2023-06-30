package ro.bogdansoftware.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.clients.review.ReviewProductPreview;
import ro.bogdansoftware.shared.security.VerifyRole;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @GetMapping("get-reviews")
    public ResponseEntity<ReviewsResponseDTO> getReviews(@RequestParam(name = "id") String productId) {
        return ResponseEntity.ok(reviewsService.getReviewsForProduct(productId));
    }

    @PostMapping("create-reviews")
    public ResponseEntity<Void> createReviews(@RequestParam(name = "productId") String productId) {
        reviewsService.createReviews(productId);
        return ResponseEntity.ok().build();
    }

    @VerifyRole("USER")
    @PutMapping("add-review")
    public ResponseEntity<Void> addReview(@RequestBody AddReviewDTO reviewDTO) {
        reviewsService.addReview(reviewDTO);
        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping("get-rating")
    public ResponseEntity<Double> getRating(@RequestParam(name = "productId") String productId) {
        return ResponseEntity.ok(reviewsService.getRatingForProduct(productId));
    }

    @GetMapping("verify-review-present")
    public ResponseEntity<Boolean> verifyReviewPresent(@RequestParam(name = "productID") String productId, @RequestParam(name = "author") String author) {
        return ResponseEntity.ok(reviewsService.verifyReviewPresent(productId, author));
    }

    @DeleteMapping("delete-reviews")
    public ResponseEntity<Void> deleteReviews(@RequestParam(name = "productID") String productId) {
        reviewsService.deleteReviews(productId);
        return ResponseEntity.noContent().build();
    }

    @VerifyRole("ADMIN")
    @DeleteMapping("delete-individual-review")
    public ResponseEntity<Void> deleteIndividualReview(@RequestParam(name = "productId") String productID, @RequestParam(name = "author") String author, @RequestParam(name = "rating") Integer rating) {
        reviewsService.deleteIndividualReview(productID, author, rating);
        return ResponseEntity.ok().build();
    }

    @PostMapping("get-rating-for-products")
    public ResponseEntity<ReviewProductPreview> getRatingForProducts(@RequestBody List<String> productIds) {
        return ResponseEntity.ok(reviewsService.getRatingForListOfProducts(productIds));
    }

}
