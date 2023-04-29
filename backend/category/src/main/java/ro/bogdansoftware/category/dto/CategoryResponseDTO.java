package ro.bogdansoftware.category.dto;

import ro.bogdansoftware.category.model.Category;
import ro.bogdansoftware.category.model.Subcategory;

import java.util.List;

public record CategoryResponseDTO(String categoryId, String name, List<Subcategory> subcategoryList) {

    public static CategoryResponseDTO convert(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName(), category.getSubcategories());
    }
}
