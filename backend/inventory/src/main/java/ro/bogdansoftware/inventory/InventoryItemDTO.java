package ro.bogdansoftware.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InventoryItemDTO {
        private String productId;
        private String productName;
        private Integer quantity;
}
