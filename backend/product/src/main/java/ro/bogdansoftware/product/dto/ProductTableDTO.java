package ro.bogdansoftware.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductTableDTO(
        String id,
        String title,
        String imagePath,
        Boolean promoActive,
        BigDecimal price,
        Boolean isAvailable,
        String category,
        String subcategory,
        LocalDateTime creationTime,
        Boolean isEnabled
) {
}
