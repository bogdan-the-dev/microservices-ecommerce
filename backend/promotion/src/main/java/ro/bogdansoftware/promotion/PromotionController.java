package ro.bogdansoftware.promotion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/promotion")
public class PromotionController {
    private final PromotionService service;

    @GetMapping(value = "get-promotion")
    public ResponseEntity<Promotion> getPromotion(@RequestParam(name = "id")String id) {
        return ResponseEntity.ok(service.getPromotion(id));
    }
    @GetMapping(value = "get-all")
    public ResponseEntity<List<Promotion>> getPromotions() {
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping(value = "get-active")
    public ResponseEntity<List<Promotion>> getActive() {
        return ResponseEntity.ok(service.getActive());
    }
    @PostMapping(value = "create")
    public ResponseEntity<Void> create(@RequestBody Promotion p) {
        service.createPromotion(p);
        return ResponseEntity.ok().build();
    }
    @PutMapping(value = "enable")
    public ResponseEntity<Void> enable(@RequestBody List<String> ids) {
        service.enablePromotions(ids);
        return ResponseEntity.ok().build();
    }
    @PutMapping(value = "disable")
    public ResponseEntity<Void> disable(@RequestBody List<String> ids) {
        service.disablePromotion(ids);
        return ResponseEntity.ok().build();
    }
    @PutMapping(value = "edit")
    public ResponseEntity<Void> edit(@RequestBody Promotion p) {
        service.editPromotion(p);
        return ResponseEntity.ok().build();
    }
    @PutMapping(value = "delete")
    public ResponseEntity<Void> delete(@RequestBody List<String> ids) {
        service.deletePromotion(ids);
        return ResponseEntity.ok().build();
    }
}
