package ro.bogdansoftware.product.model;

import java.util.List;

public record Subcategory(String id, List<String> tags, String name) {
}
