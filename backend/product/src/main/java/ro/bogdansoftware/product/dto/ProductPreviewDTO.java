package ro.bogdansoftware.product.dto;

import lombok.Builder;
import ro.bogdansoftware.product.model.Promotion;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductPreviewDTO(
        String id,
        String title,
        String imagePath,
        Boolean promoActive,
        PromotionDTO promo,
        BigDecimal price,
        Double rating,
        Integer numberOfReviews,
        Boolean isAvailable,
        List<String> tags
) {
}
