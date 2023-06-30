package ro.bogdansoftware.review;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Builder
@Document
@RequiredArgsConstructor
@AllArgsConstructor
public class Reviews {
    @Id
    private String productId;
    private List<IndividualReview> oneStar;
    private List<IndividualReview> twoStar;
    private List<IndividualReview> threeStar;
    private List<IndividualReview> fourStar;
    private List<IndividualReview> fiveStar;

    public Reviews(String productId) {
        this.productId = productId;
        oneStar = new LinkedList<>();
        twoStar = new LinkedList<>();
        threeStar = new LinkedList<>();
        fourStar = new LinkedList<>();
        fiveStar = new LinkedList<>();
    }

    public double getRating() {
        return (double) (oneStar.size() + twoStar.size() * 2 + threeStar.size() * 3 + fourStar.size() * 4 + fiveStar.size() * 5) / (oneStar.size() + twoStar.size() + threeStar.size() + fourStar.size() + fiveStar.size());
    }

    public Integer getNumberOfRatings() {
        int number = 0;
        for(int i = 1; i < 5; i++) {
            i += getListWithRating(i).size();
        }
        return number;
    }

    public ReviewsResponseDTO convertToDTO() {
        return new ReviewsResponseDTO(
            oneStar, twoStar,threeStar,fourStar,fiveStar
        );
    }

    public List<IndividualReview> getListWithRating(int rating) {
        switch (rating) {
            case 1 -> {
                return oneStar;
            }
            case 2 -> {
                return twoStar;
            }
            case 3 -> {
                return threeStar;
            }
            case 4 -> {
                return fourStar;
            }
            case 5 -> {
                return fiveStar;
            }
            default -> throw new IllegalArgumentException("Invalid rating value");
        }
    }

    public void setListWithRating(int rating, List<IndividualReview> individualReviews) {
        switch (rating) {
            case 1 -> oneStar = individualReviews;
            case 2 -> twoStar = individualReviews;
            case 3 -> threeStar = individualReviews;
            case 4 -> fourStar = individualReviews;
            case 5 -> fiveStar = individualReviews;
            default -> throw new IllegalArgumentException("Invalid rating value");
        }
    }
}
