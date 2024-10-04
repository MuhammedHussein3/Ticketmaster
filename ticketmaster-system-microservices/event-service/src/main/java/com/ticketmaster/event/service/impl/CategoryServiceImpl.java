package com.ticketmaster.event.service.impl;

import com.ticketmaster.event.dto.CategoriesDto;
import com.ticketmaster.event.dto.CategoryRequest;
import com.ticketmaster.event.entity.Category;
import com.ticketmaster.event.mapper.CategoryMapper;
import com.ticketmaster.event.repository.CategoryRepository;
import com.ticketmaster.event.service.CategoryService;
import io.micrometer.common.util.StringUtils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {

        var category = mapToCategory(categoryRequest);
        return categoryRepository.save(category);
    }

    private Category mapToCategory(CategoryRequest categoryRequest){
        return categoryMapper.mapToCategory(categoryRequest);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Category getCategoryById(Integer categoryId) {
        return findCategory(categoryId);
    }

    @Override
    public Category updateCategory(Integer categoryId, CategoryRequest categoryRequest) {

        var category = findCategory(categoryId);

        mergeCategoryDetails(category, categoryRequest);

        return categoryRepository.save(category);
    }

    private final void mergeCategoryDetails(Category category, CategoryRequest categoryRequest){

        if (StringUtils.isNotBlank(categoryRequest.name())) {
            category.setName(categoryRequest.name());
        }

        if (StringUtils.isNotBlank(categoryRequest.description())){
            category.setDescription(categoryRequest.description());
        }
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        var category = findCategory(categoryId);

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoriesDto> getAllCategories() {


        return categoryRepository.getAllCategories()
                .stream()
                .map((c)->{
                  return new CategoriesDto(c.getName(), c.getDescription());
                })
                .toList();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = {NotFoundException.class})
    private Category findCategory(Integer categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CannotCreateTransactionException(String.format("Category not found with ID:: %d", categoryId)));
    }
}
