package ro.bogdansoftware.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Builder
public record ReviewsResponseDTO(
    List<IndividualReview> oneStar,
    List<IndividualReview> twoStar,
    List<IndividualReview> threeStar,
    List<IndividualReview> fourStar,
    List<IndividualReview> fiveStar
){}
