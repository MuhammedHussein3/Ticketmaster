package com.ticketmaster.search.service.impl;


import com.ticketmaster.search.dto.SearchEvent;
import com.ticketmaster.search.entity.SearchEventDTO;
import com.ticketmaster.search.mapper.SearchMapper;
import com.ticketmaster.search.repo.SearchRepository;
import com.ticketmaster.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {


    private final SearchRepository searchRepository;
    private final SearchMapper searchMapper;

    @Override
    public void createEvent(SearchEvent eventCreateRequest) {

        SearchEventDTO searchEventDTO = mapToSearchEventDto(eventCreateRequest);
        searchRepository.save(searchEventDTO);
    }
    private SearchEventDTO mapToSearchEventDto(SearchEvent searchEvent){
        return searchMapper.mapToSearchEventEntity(searchEvent);
    }
}
