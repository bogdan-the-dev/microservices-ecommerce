package ro.bogdansoftware.category.dto;

import java.util.List;

public record UpdateCategoryRequestDTO(String id, String name, List<UpdateSubcategoryDTO> subcategories) {
}
