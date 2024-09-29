package com.ticketmaster.event.repository;

import com.ticketmaster.event.entity.Category;
import com.ticketmaster.event.projection.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("""
       SELECT c.name AS name, c.description AS description
       FROM Category c
       """)
    List<Categories> getAllCategories();
}
