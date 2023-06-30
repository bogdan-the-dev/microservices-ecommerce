package ro.bogdansoftware.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    private String id;
    private String name;
    private List<String> tags;

    public Subcategory(String name) {
        this.id = ObjectId.get().toString();
        this.name = name;

    }
}
