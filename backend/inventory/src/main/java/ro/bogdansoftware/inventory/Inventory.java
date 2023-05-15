package ro.bogdansoftware.inventory;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;

@RedisHash("Inventory")
@Builder
public class Inventory implements Serializable {
    @Id
    String productID;
    Integer remainingQuantity;
}
