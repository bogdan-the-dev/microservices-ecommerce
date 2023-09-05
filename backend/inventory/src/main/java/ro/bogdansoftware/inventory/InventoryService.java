package ro.bogdansoftware.inventory;

import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.inventory.InventoryDTO;
import ro.bogdansoftware.clients.product.IProductClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final static Log log = LogFactory.getLog(InventoryService.class);

    private final IProductClient productClient;

    public Integer getAvailableQuantity(String id) {
        var res = redisTemplate.opsForHash().get("Inventory", id);
        if(res == null)
        {
            log.warn("Product's id was not found in the inventory");
            return -1;
        }
        return (Integer)res;
    }

    public void createInventory(InventoryDTO dto) {
        Inventory inventory = Inventory.builder()
                .productID(dto.productId())
                .remainingQuantity(dto.quantity())
                .build();
        redisTemplate.opsForHash().put("Inventory",inventory.productID, inventory.remainingQuantity);
    }


    public void modifyInventory(String id, int quantity) {
        int actualQuantity = (int) redisTemplate.opsForHash().get("Inventory", id);
        if(quantity == 0) {
            productClient.changeInventoryStatus(id,false);
        }
        else if(actualQuantity == 0 && quantity > 0) {
            productClient.changeInventoryStatus(id, true);
        }
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.multi();
                operations.opsForHash().put((K)"Inventory", id, quantity);
                operations.exec();
                return null;
            }
        });
    }

    public boolean orderModifyInventory(Map<String, Integer> orderItems) {
        try {
            redisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {

                    operations.watch((K) "Inventory");
                    List<Integer> qty = new ArrayList<>(orderItems.size());
                    for (Map.Entry<String, Integer> item : orderItems.entrySet()) {
                        if(operations.opsForHash().get((K) "Inventory", item.getKey()) == null) {
                            throw new RuntimeException("Invalid inventory id: " + item.getKey());
                        }
                        qty.add((Integer) operations.opsForHash().get((K) "Inventory", item.getKey()) - item.getValue());
                    }

                    operations.multi();

                    int index = 0;
                    for (String itemId : orderItems.keySet()) {
                        var localQty = qty.get(index);
                        if(localQty == 0) {
                            productClient.changeInventoryStatus(itemId,false);
                        }
                        if (localQty < 0) {
                            operations.discard();
                            throw new RuntimeException("Ordered quantity greater than available stock");
                        }
                        else {
                            operations.opsForHash().put((K) "Inventory", itemId, qty.get(index));
                            index++;
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

    public void deleteInventory(String id) {
        redisTemplate.opsForHash().delete("Inventory", id);
    }

    public void returnInventory(String itemId, int quantity) {
        int actual = (int) redisTemplate.opsForHash().get("Inventory", itemId);
        modifyInventory(itemId, actual+quantity);
    }

    public List<InventoryItemDTO> getAllInventory() {
        Set<Object> ids = redisTemplate.opsForHash().keys("Inventory");
        List<InventoryItemDTO> items = new ArrayList<>();
        for(Object id :  ids) {
            InventoryItemDTO item = new InventoryItemDTO();
            item.setProductId(id.toString());
            item.setQuantity((Integer) redisTemplate.opsForHash().get("Inventory", id));
            items.add(item);
        }

        List<String> itemIds = items.stream().map(InventoryItemDTO::getProductId).collect(Collectors.toList());
        List<String> names = productClient.getProductNames(itemIds).getBody();
        for(InventoryItemDTO item : items) {
            item.setProductName(names.get(0));
            names.remove(0);
        }

        return items;
    }

}
