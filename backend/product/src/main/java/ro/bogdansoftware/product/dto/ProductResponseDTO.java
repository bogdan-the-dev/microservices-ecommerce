package ro.bogdansoftware.product.dto;

import ro.bogdansoftware.product.Serializer;
import ro.bogdansoftware.product.model.Product;
import ro.bogdansoftware.product.model.Promotion;
import ro.bogdansoftware.product.model.Variation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ProductResponseDTO(
        String id,
        String title,
        String description,
        String category,
        BigDecimal price,
        String subcategory,
        List<String> photos,
        String variations,
        String specifications,
        Promotion promotion,
        Boolean outOfStock

) {

    public static ProductResponseDTO convert(Product p) {
        return new ProductResponseDTO(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getCategory(),
                p.getPrice(),
                p.getSubcategory(),
                p.getPhotos(),
                Serializer.serializeSpecs(p.getVariations()),
                Serializer.serializeSpecs(p.getSpecifications()),
                p.getPromotion(),
                p.isOutOfStock());
    }

    public static Product convert(ProductResponseDTO responseDTO) {
        return Product
                .builder()
                .id(responseDTO.id)
                .description(responseDTO.description)
                .variations(Serializer.deserializeSpecs(responseDTO.variations))
                .build();
    }
}
