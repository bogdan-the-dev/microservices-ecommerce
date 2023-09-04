package ro.bogdansoftware.product.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Document
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String subcategory;
    private List<String> photos;
    private Map<String, Map<String, String>> variations;
    private Map<String, Map<String, String>> specifications;
    private Promotion promotion;
    private boolean outOfStock;
    private boolean isEnabled;
    private LocalDateTime creationTimestamp;
    private LocalDateTime latestEditTimestamp;

    public String getFirstPhoto() {
        return this.photos.size() > 0 ? this.photos.get(0) : "";
    }
}
