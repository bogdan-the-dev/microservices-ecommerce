package ro.bogdansoftware.category;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.bogdansoftware.category.model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository
        extends MongoRepository<Category, String> {

    Optional<Category> getCategoryByNameIs(String name);
}
