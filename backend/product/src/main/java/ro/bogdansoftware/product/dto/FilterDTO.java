package ro.bogdansoftware.product.dto;

import ro.bogdansoftware.product.model.ProductFilter;

import java.util.List;

public record FilterDTO(List<ProductFilter> filterList) {
}
