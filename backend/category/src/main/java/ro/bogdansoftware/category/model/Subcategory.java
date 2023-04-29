package ro.bogdansoftware.category.model;

import java.util.List;

public record Subcategory(String id, List<String> tags, String name) {
}
