package ro.bogdansoftware.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.bogdansoftware.product.model.Product;

@Repository
public interface ProductRepository
        extends MongoRepository<Product, String> {
}
