package ro.bogdansoftware.clients.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProductForCartDTO {
    private String itemId;
    private String title;
    private String image;
    private BigDecimal price;
}
