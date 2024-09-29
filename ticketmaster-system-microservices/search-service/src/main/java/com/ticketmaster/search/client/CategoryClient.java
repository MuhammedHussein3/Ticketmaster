package com.ticketmaster.search.client;


import com.ticketmaster.search.message.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "event-service",
        contextId = "categoryClient",
        url = "${application.config.category-url-feignClient}"
)
public interface CategoryClient {

    @GetMapping("/{category-id}")
     ResponseEntity<Category> getCategoryById(@PathVariable(name = "category-id") Integer categoryId);

}
