package ro.bogdansoftware.product.dto;

import java.math.BigDecimal;

public record CartProductDTO(
        String itemId,
        String title,
        String image,
        BigDecimal price
) {
}
