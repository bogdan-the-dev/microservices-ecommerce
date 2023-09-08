package ro.bogdansoftware.clients.product;

import java.util.List;

public record ChangeProductsCategoryDTO(
        List<String> ids,
        String category,
        String subcategory
) {
}
