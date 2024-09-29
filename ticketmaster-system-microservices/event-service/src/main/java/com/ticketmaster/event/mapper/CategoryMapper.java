package com.ticketmaster.event.mapper;

import com.ticketmaster.event.dto.CategoryRequest;
import com.ticketmaster.event.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public Category mapToCategory(CategoryRequest categoryRequest){

        return Category.builder()
                .name(categoryRequest.name())
                .description(categoryRequest.description())
                .build();
    }
}
