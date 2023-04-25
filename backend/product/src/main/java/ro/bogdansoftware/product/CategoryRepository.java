package ro.bogdansoftware.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.bogdansoftware.product.model.Category;

@Repository
public interface CategoryRepository
        extends MongoRepository<Category, String> {
}
