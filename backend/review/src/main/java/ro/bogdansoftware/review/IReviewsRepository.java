package ro.bogdansoftware.review;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewsRepository extends MongoRepository<Reviews, String> {

    Reviews getReviewsByProductId(String productId);
}
