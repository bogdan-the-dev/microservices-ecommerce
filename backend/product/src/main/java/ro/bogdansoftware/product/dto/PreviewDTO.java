package ro.bogdansoftware.product.dto;

import java.util.List;

public record PreviewDTO(
        List<ProductPreviewDTO> products,
        Integer numberOfProducts
) {
}
