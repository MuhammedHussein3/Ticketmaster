package com.ticketmaster.search.api;

import com.ticketmaster.search.dto.EventSearchResponse;
import com.ticketmaster.search.dto.SearchCriteria;
import com.ticketmaster.search.entity.SearchEvent;
import com.ticketmaster.search.service.EventSearchService;
import com.ticketmaster.search.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final EventSearchService eventSearchService;


    @GetMapping
    public ResponseEntity<EventSearchResponse> search(
            @RequestBody SearchCriteria criteria,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDir

            ){
        return ResponseEntity.ok(eventSearchService.search(criteria, pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SearchEvent>> getAll(
    ){
        return ResponseEntity.ok(eventSearchService.getAll());
    }
}
