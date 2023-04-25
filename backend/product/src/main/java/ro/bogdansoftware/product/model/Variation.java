package ro.bogdansoftware.product.model;


import java.util.List;

public record Variation(String id, String variationCategory, List<VariationOption> variationOptions) {
}
