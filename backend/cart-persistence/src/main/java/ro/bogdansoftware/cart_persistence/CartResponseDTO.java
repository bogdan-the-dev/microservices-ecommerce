package ro.bogdansoftware.cart_persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.bogdansoftware.clients.product.ProductForCartDTO;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CartResponseDTO {
    private String itemId;
    private String title;
    private String image;
    private BigDecimal price;
    private Integer quantity;

    public CartResponseDTO(int quantity, ProductForCartDTO product) {
        this.quantity = quantity;
        image = product.getImage();
        title = product.getTitle();
        itemId = product.getItemId();
        price = product.getPrice();
    }


}
