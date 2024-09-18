package com.ticketmaster.event.service;

import com.ticketmaster.event.dto.CategoryRequest;
import com.ticketmaster.event.entity.Category;
import com.ticketmaster.event.projection.Categories;

import java.util.List;

public interface CategoryService {


        /**
         * Creates a new category.
         *
         * @param categoryRequest The category request data.
         * @return The created category.
         */
        Category createCategory(CategoryRequest categoryRequest);

        /**
         * Retrieves a category by its ID.
         *
         * @param categoryId The ID of the category.
         * @return The category with the specified ID.
         */
        Category getCategoryById(Long categoryId);

        /**
         * Updates an existing category.
         *
         * @param categoryId The ID of the category to update.
         * @param categoryRequest The new category data.
         * @return The updated category.
         */
        Category updateCategory(Long categoryId, CategoryRequest categoryRequest);

        /**
         * Deletes a category by its ID.
         *
         * @param categoryId The ID of the category to delete.
         */
        void deleteCategory(Long categoryId);

        /**
         * Retrieves all categories.
         *
         * @return A list of all categories.
         */
        List<Categories> getAllCategories();

}
