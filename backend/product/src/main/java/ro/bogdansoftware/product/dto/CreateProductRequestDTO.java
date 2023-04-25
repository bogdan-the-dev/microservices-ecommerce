package ro.bogdansoftware.product.dto;

import java.math.BigDecimal;

public record CreateProductRequestDTO(
    String name,
    String description,
    BigDecimal price
) {

}
