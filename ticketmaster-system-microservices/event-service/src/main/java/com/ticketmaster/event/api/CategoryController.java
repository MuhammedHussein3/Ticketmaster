package com.ticketmaster.event.api;

import com.ticketmaster.event.dto.CategoriesDto;
import com.ticketmaster.event.dto.CategoryRequest;
import com.ticketmaster.event.entity.Category;

import com.ticketmaster.event.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Endpoint to create a new category.
     *
     * @param categoryRequest The category request data.
     * @return ResponseEntity containing the created category.
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @Valid  @RequestBody CategoryRequest categoryRequest
    ) {
        Category createdCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve a category by ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return ResponseEntity containing the category.
     */
    @GetMapping("/{category-id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "category-id") Integer categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Endpoint to update an existing category by ID.
     *
     * @param categoryId      The ID of the category to update.
     * @param categoryRequest The new category data.
     * @return ResponseEntity containing the updated category.
     */
    @PutMapping("/{category-id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(name = "category-id") Integer categoryId,
                                                   @RequestBody CategoryRequest categoryRequest) {
        Category updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * Endpoint to delete a category by ID.
     *
     * @param categoryId The ID of the category to delete.
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/{category-id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "category-id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint to retrieve all categories.
     *
     * @return ResponseEntity containing a list of all categories.
     */
    @GetMapping
    public ResponseEntity<List<CategoriesDto>> getAllCategories() {
        var categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}

