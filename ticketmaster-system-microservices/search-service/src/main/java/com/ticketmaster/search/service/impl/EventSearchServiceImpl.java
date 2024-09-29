package com.ticketmaster.search.service.impl;

import com.ticketmaster.search.dto.EventSearchResponse;
import com.ticketmaster.search.dto.SearchCriteria;
import com.ticketmaster.search.entity.SearchEvent;
import com.ticketmaster.search.repo.SearchQueryBuilder;
import com.ticketmaster.search.repo.SearchRepository;
import com.ticketmaster.search.service.EventSearchService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventSearchServiceImpl implements EventSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final SearchRepository searchRepository;
    @Override
    public EventSearchResponse search(SearchCriteria criteria,
                                      int pageNo,
                                      int pageSize,
                                      String sortBy,
                                      String sortDir) {

        // Create a Criteria object for building your query
        Criteria criteriaBuilder = new SearchQueryBuilder()
                .withEventName(criteria.eventName())
                .withDescription(criteria.description())
                .withVenueName(criteria.venueName())
                .withDate(criteria.dateFrom(), criteria.dateTo())
                .withLocation(criteria.location())
                .withCategory(criteria.category())
                .withKeywords(criteria.keywords())
                .build();

        Pageable pageable = getPageable(pageNo, pageSize, sortBy, sortDir);

        Query query = new CriteriaQuery(criteriaBuilder).setPageable(pageable);
        SearchHits<SearchEvent> searchHits = elasticsearchOperations.search(query, SearchEvent.class);

        List<SearchEvent> events = searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();


       return buildSearchResponse(events, pageNo, pageSize, searchHits);
    }

    private EventSearchResponse buildSearchResponse(List<SearchEvent> events,
                                                    int pageNo,
                                                    int pageSize,
                                                    SearchHits<SearchEvent> searchHits){

        long totalElements = searchHits.getTotalHits();
        int totalPages = (int)Math.ceil((double) totalElements / pageSize);
        boolean isLast = pageNo + 1 >= totalPages;

        return EventSearchResponse.builder()
                .events(events)
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .totalElement(totalElements)
                .totalPages(totalPages)
                .isLast(isLast)
                .build();
    }

    private Pageable getPageable(int pageNo,
                                 int pageSize,
                                 String sortBy,
                                 String sortDir){

        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortDir).ascending()
                : Sort.by(sortDir).descending();
        return PageRequest.of(pageNo, pageSize, sort);
    }



    // To Test
    @Override
    public List<SearchEvent> getAll() {

        Query query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(m -> m))
                .build();

        SearchHits<SearchEvent> searchHits = elasticsearchOperations.search(query, SearchEvent.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
