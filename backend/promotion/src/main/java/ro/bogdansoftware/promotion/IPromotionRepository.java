package ro.bogdansoftware.promotion;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPromotionRepository extends MongoRepository<Promotion, String> {
    void deletePromotionById(String id);

    List<Promotion> getPromotionsByActiveIsTrue();
}
