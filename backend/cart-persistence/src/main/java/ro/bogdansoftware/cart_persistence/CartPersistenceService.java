package ro.bogdansoftware.cart_persistence;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.product.IProductClient;
import ro.bogdansoftware.clients.product.ProductForCartDTO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartPersistenceService {
    private final RedisTemplate<String, List<CartItem>> template;
    private final IProductClient productClient;

    public List<CartResponseDTO> getCart(String username) {
        List<CartItem> res = (List<CartItem>) template.opsForHash().get("Cart", username);

        var products = productClient.getProductForCart(res.stream().map(CartItem::getItemId).collect(Collectors.toList())).getBody();
        List<CartResponseDTO> response = new ArrayList<>();
        for(ProductForCartDTO product: products) {
            int quantity = res.stream().filter(elem -> Objects.equals(elem.getItemId(), product.getItemId())).findFirst().get().getQuantity();
            response.add(new CartResponseDTO(quantity, product));
        }
        return response;
    }

    public void addItem(CartItem item, String username) {
        List<CartItem> list = (List<CartItem>) template.opsForHash().get("Cart", username);
        if(list == null) {
            list = new LinkedList<>();
        }
        boolean itemPresent = false;
        for(CartItem cartItem: list) {
            if(Objects.equals(cartItem.getItemId(), item.getItemId())) {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                itemPresent = true;
            }
        }
        if(!itemPresent)
        {
            list.add(item);
        }
        template.opsForHash().put("Cart", username, list);
    }

    public void removeItem(String itemId, String username) {
        List<CartItem> list = (List<CartItem>) template.opsForHash().get("Cart", username);
        if(list == null) {
            return;
        }
        list = list.stream().filter(item -> !Objects.equals(item.getItemId(), itemId)).collect(Collectors.toList());
        template.opsForHash().put("Cart", username, list);
    }

    public void emptyCart(String username) {
        template.opsForHash().put("Cart", username, new LinkedList<>());
    }
}
