package ro.bogdansoftware.clients.inventory;

import java.util.Map;

public record ListOfInventoryItemsDTO(
    Map<String, Integer> orderItems
) {
}
