package ro.bogdansoftware.promotion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.product.IProductClient;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final IPromotionRepository repository;
    private final IProductClient productClient;

    public void createPromotion(Promotion p) {
        p.setCreationTimestamp(LocalDateTime.now());
        repository.save(p);
    }

    public void enablePromotions(List<String> ids) {
        for (String id: ids) {
            Promotion p = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid promotion id"));
            p.setActive(true);
            repository.save(p);
        }
    }

    public void disablePromotion(List<String> ids) {
        for (String id: ids) {
            Promotion p = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid promotion id"));
            if(productClient.removePromotions(id).getStatusCode().value() == 200) {
                repository.deletePromotionById(id);
            }
            p.setActive(false);
            repository.save(p);
        }
    }

    public void editPromotion(Promotion promotion) {
        Promotion savedPromotion = repository.findById(promotion.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid promotion id"));
        savedPromotion.setName(promotion.getName());
        savedPromotion.setPercentage(promotion.getPercentage());
        repository.save(savedPromotion);
    }

    public void deletePromotion(List<String> ids) {
        for(String id : ids) {
            Promotion p = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid promotion id"));
            if(productClient.removePromotions(id).getStatusCode().value() == 200) {
                repository.deletePromotionById(id);
            }
        }
    }

    public Promotion getPromotion(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid promotion id"));
    }

    public List<Promotion> getAll() {
        return repository.findAll();
    }

    public List<Promotion> getActive() {
        return repository.getPromotionsByActiveIsTrue();
    }
}
