package ro.bogdansoftware.product.model;

public record ProductFilter(String fieldName, ComparisonType comparisonType, String value) {
}
