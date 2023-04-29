package ro.bogdansoftware.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document
@AllArgsConstructor
public class Category {
    @Id
    private String id;
    private String name;
    private List<Subcategory> subcategories;
    private List<String> tags;

    public Category() {
    }
}
