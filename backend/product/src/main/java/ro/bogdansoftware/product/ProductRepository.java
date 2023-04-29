package ro.bogdansoftware.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.bogdansoftware.product.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository
        extends MongoRepository<Product, String> {

    Optional<List<Product>> findAllByCategoryIdIs(String categoryId);
}
