package com.ticketmaster.search.repo;

import com.ticketmaster.search.entity.SearchEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends ElasticsearchRepository<SearchEvent, Long> {
}
