package com.ticketmaster.search.service;

import com.ticketmaster.search.dto.EventSearchResponse;
import com.ticketmaster.search.dto.SearchCriteria;
import com.ticketmaster.search.entity.SearchEvent;


import java.util.List;

public interface EventSearchService {

    EventSearchResponse search(SearchCriteria criteria, int pageNo, int pageSize, String sortBy, String sortDir);

    List<SearchEvent> getAll();
}
