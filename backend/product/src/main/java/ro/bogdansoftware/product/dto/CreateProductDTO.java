package ro.bogdansoftware.product.dto;

import ro.bogdansoftware.product.model.Promotion;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductDTO(
        String id,
        String title,
        String description,
        BigDecimal price,
        String category,
        String subcategory,
        List<String> photos,
        String specifications,
        Promotion promotion,
        Boolean outOfStock,
        Integer initialQuantity
) {
}
