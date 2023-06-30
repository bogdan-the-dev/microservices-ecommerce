package ro.bogdansoftware.category.dto;

import java.util.List;

public record CreateCategoryRequestDTO(
        String name,
        List<String> subcategories
) {
}
