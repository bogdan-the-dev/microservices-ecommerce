package ro.bogdansoftware.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.category.dto.CategoryResponseDTO;
import ro.bogdansoftware.category.dto.CreateCategoryRequestDTO;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "/api/v1/category")
@Slf4j
public class CategoryController {

        private final CategoryService service;

        public CategoryController(CategoryService service) {
                this.service = service;
        }


        @GetMapping(value = "get-all-categories")
        @ResponseBody
        public ResponseEntity<List<CategoryResponseDTO>> getAll() {
                return ResponseEntity.ok(
                        this.service
                                .getAllCategories()
                                .stream()
                                .map(CategoryResponseDTO::convert)
                                .collect(Collectors.toList()));
        }

        @GetMapping("get-category")
        public ResponseEntity<CategoryResponseDTO> getCategory(@RequestParam String id) {
                return ResponseEntity.ok(
                        CategoryResponseDTO.convert(this.service.getCategory(id))
                );
        }

        @GetMapping("get-category-name")
        public ResponseEntity<String> getCategoryName(@RequestParam String name) {
                return ResponseEntity.ok(
                        this.service.getCategoryIdFromName(name)
                );
        }

        @PostMapping(value = "add-category")
        public ResponseEntity<Void> addCategory(CreateCategoryRequestDTO requestDTO) {
                this.service.addCategory(requestDTO);
                return ResponseEntity.created(URI.create("")).build();
        }

}
