package ro.bogdansoftware.product.dto;

import ro.bogdansoftware.product.model.Promotion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ViewEditProductDTO(
        String id,
        String title,
        String description,
        BigDecimal price,
        String category,
        String subcategory,
        List<String> photos,
        Map<String, Map<String, String>> specifications,
        Promotion promotion,
        Boolean outOfStock,
        Boolean isEnabled
) {
}
