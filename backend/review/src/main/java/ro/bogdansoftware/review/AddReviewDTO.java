package ro.bogdansoftware.review;

public record AddReviewDTO(
        String productId,
        Integer rating,
        String review
) {
}
