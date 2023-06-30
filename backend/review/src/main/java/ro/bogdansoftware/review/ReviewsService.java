package ro.bogdansoftware.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.review.ReviewProductPreview;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final IReviewsRepository repository;

    public ReviewsResponseDTO getReviewsForProduct(String productId) {
        Reviews reviews = repository.getReviewsByProductId(productId);
        return reviews.convertToDTO();
    }

    public void createReviews(String productId) {
        Reviews reviews = new Reviews(productId);
        this.repository.insert(reviews);
    }

    public void addReview(AddReviewDTO reviewDTO) {
        Reviews reviews = repository.getReviewsByProductId(reviewDTO.productId());
        IndividualReview individualReview = IndividualReview.builder()
                .username(reviewDTO.username())
                .review(reviewDTO.review())
                .build();

        reviews.getListWithRating(reviewDTO.rating()).add(individualReview);

        repository.save(reviews);
    }

    public double getRatingForProduct(String productId) {
        Reviews reviews = repository.getReviewsByProductId(productId);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(reviews.getRating()));
    }

    public void deleteReviews(String productId) {
        Reviews reviews = repository.getReviewsByProductId(productId);
        repository.delete(reviews);
    }

    public void deleteIndividualReview(String productId, String author, Integer rating) {
        Reviews reviews  = repository.getReviewsByProductId(productId);
        var newList = reviews.getListWithRating(rating).stream().filter(review -> !Objects.equals(review.getUsername(), author)).collect(Collectors.toList());
        reviews.setListWithRating(rating, newList);
        repository.save(reviews);
    }

    public boolean verifyReviewPresent(String productId, String author) {
        Reviews reviews = repository.getReviewsByProductId(productId);
        for(int i = 1; i <= 5; i++) {
            var list = reviews.getListWithRating(i);
            if (list.stream().anyMatch(elem -> Objects.equals(elem.getUsername(), author))) {
                return true;
            }
        }
        return false;
    }

    public ReviewProductPreview getRatingForListOfProducts(List<String> productIds) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        ReviewProductPreview reviewProductPreview = new ReviewProductPreview();
        for(String productId: productIds) {
            Reviews reviews = repository.getReviewsByProductId(productId);
            reviewProductPreview.getRatings().add(((Double.parseDouble(decimalFormat.format(reviews.getRating())))));
            reviewProductPreview.getNumberOfRatings().add(reviews.getNumberOfRatings());
        }
        return reviewProductPreview;
    }

}
