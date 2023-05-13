package ro.bogdansoftware.product.dto;

import ro.bogdansoftware.product.model.Product;
import ro.bogdansoftware.product.model.Variation;

import java.util.List;

public record ProductResponseDTO(
        String id,
        String name,
        String description,
        //String category,
        //String subCategory,
        List<Variation> variations
) {

    public static ProductResponseDTO convert(Product p) {
        return new ProductResponseDTO(p.getId(), p.getName(), p.getDescription(), p.getVariations());
    }

    public static Product convert(ProductResponseDTO responseDTO) {
        return Product
                .builder()
                .id(responseDTO.id)
                .description(responseDTO.description)
                .variations(responseDTO.variations)
                .build();
    }
}
