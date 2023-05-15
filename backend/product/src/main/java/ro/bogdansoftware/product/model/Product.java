package ro.bogdansoftware.product.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Document
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryId;
    private String subcategoryId;
    private String image;
    private List<Variation> variations;
    private Promotion promotion;
    private boolean isInventoryEmpty;



}
