package com.ticketmaster.event.service.impl

import com.ticketmaster.event.dto.CategoriesDto
import com.ticketmaster.event.dto.CategoryRequest
import com.ticketmaster.event.entity.Category
import com.ticketmaster.event.exceptions.CategoryNotFoundException
import com.ticketmaster.event.mapper.CategoryMapper
import com.ticketmaster.event.projection.Categories
import com.ticketmaster.event.repository.CategoryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.mockito.Mockito.*

@ExtendWith(MockitoExtension::class)
class CategoryServiceImplTest {
}
