package ro.bogdansoftware.inventory;

import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {

    private final IInventoryRepository repository;
    private final RedisTemplate<String, Integer> redisTemplate;
    private final static Log log = LogFactory.getLog(InventoryService.class);

    public int getAvailableQuantity(String id) {
        Optional<Inventory> opt = repository.findById(id);
        if (opt.isEmpty()) {
            log.warn("Product's id was not found in the inventory");
        }
        else {
            return opt.get().remainingQuantity;
        }
        return -1;
    }

    public boolean incrementInventory(String id, int quantity) {
        Optional<Inventory> opt = repository.findById(id);
        if(opt.isEmpty()) {
            log.warn(String.format("Inventory with id [%s] doesn't exist", id));
            return false;
        }
        Inventory i = opt.get();
        i.remainingQuantity += quantity;
        repository.save(i);
        return true;
    }

    public void modifyInventory(String id, int quantity) {
        log.info(String.format("Inventory for product with id [%s] was changed to [%d]", id, quantity));
        Inventory i = repository.findById(id).orElse(Inventory.builder().productID(id).build());
        if(i.remainingQuantity == 0) {
            //todo send notification to disable product
        }
        i.remainingQuantity = quantity;
        repository.save(i);
    }

    public boolean orderModifyInventory(Map<String, Integer> orderItems) {
        try {
            redisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    operations.multi();
                    for (Map.Entry<String, Integer> item : orderItems.entrySet()) {
                        long newQuantity = operations.opsForHash().increment((K) "Inventory", item.getKey(), -item.getValue());
                        if (newQuantity < 0) {
                            operations.discard();
                            throw new RuntimeException("Ordered quantity greater than available stock");
                        }
                    }
                    operations.exec();
                    return null;
                }

            });
        } catch (RuntimeException ex) {
            log.warn(ex.getMessage());
            return false;
        }
        return true;
    }
}
