package ro.bogdansoftware.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.category.dto.CreateCategoryRequestDTO;
import ro.bogdansoftware.category.model.Category;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories() {
        return this.repository.findAll();
    }

    public Category getCategory(String id) throws RuntimeException{
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Category with id " + id + " was not found"));
    }

    public void deleteCategory(String categoryId) {
        this.repository.deleteById(categoryId);

        //TODO move all the products from that category to uncategorized


    }

    public void addCategory(CreateCategoryRequestDTO requestDTO) {
        this.repository.insert(Category.builder().name(requestDTO.name()).build());
    }

    public String getCategoryIdFromName(String name) {
        var categoryOptional = this.repository.getCategoryByNameIs(name);
        return  categoryOptional.isPresent() ? categoryOptional.get().getId() : "";
    }

}
