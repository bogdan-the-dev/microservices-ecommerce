package ro.bogdansoftware.clients.category;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "category",
        path = "api/v1/category"
)
public interface CategoryClient {
    @GetMapping("get-category-id")
    ResponseEntity<String> getCategoryId(@RequestParam("name") String name);
}
