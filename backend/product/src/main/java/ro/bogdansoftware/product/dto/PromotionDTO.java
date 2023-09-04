package ro.bogdansoftware.product.dto;

import java.math.BigDecimal;

public record PromotionDTO(
        String name,
        BigDecimal percentage) {
}
