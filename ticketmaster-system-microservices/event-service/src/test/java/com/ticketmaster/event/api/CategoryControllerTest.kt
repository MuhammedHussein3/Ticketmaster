package com.ticketmaster.event.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ticketmaster.event.dto.CategoriesDto
import com.ticketmaster.event.dto.CategoryRequest
import com.ticketmaster.event.entity.Category
import com.ticketmaster.event.service.CategoryService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CategoryController::class)
class CategoryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var categoryService: CategoryService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var category: Category
    private lateinit var categoryRequest: CategoryRequest

    @BeforeEach
    fun setUp() {
        category = Category.builder()
            .id(1)
            .name("Sports Category")
            .description("category description")
            .build()

        categoryRequest = CategoryRequest.builder()
            .name("Sports Category")
            .description("category description")
            .build()
    }

    @Test
    fun `should create category`() {
        whenever(categoryService.createCategory(any())).thenReturn(category)

        mockMvc.perform(post("/api/v1/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoryRequest)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(category.id))
            .andExpect(jsonPath("$.name").value(category.name))
            .andExpect(jsonPath("$.description").value(category.description))

        verify(categoryService, times(1)).createCategory(any<CategoryRequest>())
    }

    @Test
    fun `should get category by id`() {
        whenever(categoryService.getCategoryById(1)).thenReturn(category)

        mockMvc.perform(get("/api/v1/categories/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(category.id))
            .andExpect(jsonPath("$.name").value(category.name))
            .andExpect(jsonPath("$.description").value(category.description))

        verify(categoryService, times(1)).getCategoryById(1)
    }

    @Test
    fun `should update category`() {
        val updatedCategory = CategoryRequest.builder()
            .name("Updated Category")
            .description("Updated Category Description")
            .build()

        val existingCategory = Category.builder()
            .id(1)
            .name("Updated Category")
            .description("Updated Category Description")
            .build()

        whenever(categoryService.updateCategory(eq(1), any())).thenReturn(existingCategory)

        mockMvc.perform(put("/api/v1/categories/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedCategory)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(existingCategory.id))
            .andExpect(jsonPath("$.name").value(existingCategory.name))
            .andExpect(jsonPath("$.description").value(existingCategory.description))

        verify(categoryService, times(1)).updateCategory(eq(1), any<CategoryRequest>())
    }

    @Test
    fun `should delete category`() {
        doNothing().whenever(categoryService).deleteCategory(1)

        mockMvc.perform(delete("/api/v1/categories/1"))
            .andExpect(status().isNoContent)

        verify(categoryService, times(1)).deleteCategory(1)
    }

    @Test
    fun `should get all categories`() {
        val categoryDto = CategoriesDto.builder()
            .name("Sample Category")
            .build()

        val categories = listOf(categoryDto)
        whenever(categoryService.getAllCategories()).thenReturn(categories)

        mockMvc.perform(get("/api/v1/categories"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value(categoryDto.name))

        verify(categoryService, times(1)).getAllCategories()
    }
}
