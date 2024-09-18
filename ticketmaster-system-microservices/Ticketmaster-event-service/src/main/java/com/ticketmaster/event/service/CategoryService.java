package com.ticketmaster.event.service;

import com.ticketmaster.event.dto.CategoryRequest;
import com.ticketmaster.event.entity.Category;

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

    
}
