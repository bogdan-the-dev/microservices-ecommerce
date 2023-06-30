package ro.bogdansoftware.category.dto;

import ro.bogdansoftware.category.model.Category;
import ro.bogdansoftware.category.model.Subcategory;

import java.util.List;

public record CategoryResponseDTO(String id, String name, List<Subcategory> subcategories) {

    public static CategoryResponseDTO convert(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName(), category.getSubcategories());
    }
}
