package ro.bogdansoftware.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.category.dto.CreateCategoryRequestDTO;
import ro.bogdansoftware.category.dto.UpdateCategoryRequestDTO;
import ro.bogdansoftware.category.dto.UpdateSubcategoryDTO;
import ro.bogdansoftware.category.model.Category;
import ro.bogdansoftware.category.model.Subcategory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        this.repository.insert(Category.builder()
                .name(requestDTO.name())
                .subcategories(requestDTO.subcategories().stream().map(Subcategory::new).collect(Collectors.toList()))
                .build());
    }

    public void editCategory(UpdateCategoryRequestDTO requestDTO) {
        Category category = repository.findById(requestDTO.id()).orElseThrow(() -> new IllegalArgumentException("Invalid category id"));
        category.setName(requestDTO.name());

        category.getSubcategories().removeIf(elem -> requestDTO.subcategories().stream().noneMatch(requestElem -> Objects.equals(requestElem.id(), elem.getId())));

        List<UpdateSubcategoryDTO> addNew = new LinkedList<>();

        for(UpdateSubcategoryDTO s : requestDTO.subcategories()) {
            Optional<Subcategory> optionalSubcategory = category.getSubcategories().stream().filter(elem -> Objects.equals(elem.getId(), s.id())).findFirst();
            if(optionalSubcategory.isPresent()) {

                //todo send request to change the subcategory name on the products

                optionalSubcategory.get().setName(s.name());
            } else {
                addNew.add(s);
            }
        }
        addNew.forEach(elem -> category.getSubcategories().add(new Subcategory(elem.name())));

        repository.save(category);
    }

    public String getCategoryIdFromName(String name) {
        log.info("Get category id for name " + name);
        var categoryOptional = this.repository.getCategoryByNameIs(name);
        return  categoryOptional.isPresent() ? categoryOptional.get().getId() : "";
    }



}
