package ro.bogdansoftware.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.category.dto.CategoryResponseDTO;
import ro.bogdansoftware.category.dto.CreateCategoryRequestDTO;
import ro.bogdansoftware.category.dto.UpdateCategoryRequestDTO;
import ro.bogdansoftware.shared.security.VerifyRole;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api/v1/category")
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

        @GetMapping("get-category-id")
        public ResponseEntity<String> getCategoryId(@RequestParam("name") String name) {
                return ResponseEntity.ok(this.service.getCategoryIdFromName(name));
        }

        @PostMapping(value = "add-category")
        @VerifyRole("ADMIN")
        public ResponseEntity<Void> addCategory(@RequestBody CreateCategoryRequestDTO requestDTO) {
                this.service.addCategory(requestDTO);
                return ResponseEntity.created(URI.create("")).build();
        }
        @PutMapping(value = "edit-category")
        @VerifyRole("ADMIN")
        public ResponseEntity<Void> editCategory(@RequestBody UpdateCategoryRequestDTO requestDTO) {
                this.service.editCategory(requestDTO);
                return ResponseEntity.noContent().build();
        }
        @DeleteMapping("delete-category")
        @VerifyRole("ADMIN")
        public ResponseEntity<Void> deleteCategory(@RequestParam (name = "id") String id) {
                this.service.deleteCategory(id);
                return ResponseEntity.ok().build();
        }
}
