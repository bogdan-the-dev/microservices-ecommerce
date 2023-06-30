package ro.bogdansoftware.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

public record CartProductDTO(
        String itemId,
        String title,
        String image,
        BigDecimal price
) {
}
