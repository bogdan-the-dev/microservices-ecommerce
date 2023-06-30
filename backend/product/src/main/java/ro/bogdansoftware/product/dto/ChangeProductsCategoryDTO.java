package ro.bogdansoftware.product.dto;

import java.util.List;

public record ChangeProductsCategoryDTO(
        List<String> ids,
        String category,
        String subcategory
) {
}
