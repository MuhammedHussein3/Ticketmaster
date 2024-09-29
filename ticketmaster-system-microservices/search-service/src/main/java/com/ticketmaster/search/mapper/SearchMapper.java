package com.ticketmaster.search.mapper;

import com.ticketmaster.search.dto.VenueResponse;
import com.ticketmaster.search.entity.SearchEvent;
import com.ticketmaster.search.message.Category;
import com.ticketmaster.search.message.EventMessageCDC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchMapper {


    public SearchEvent mapToSearchEvent(EventMessageCDC messageCDC, Category category, VenueResponse venueResponse){

        return SearchEvent.builder()
                .id(messageCDC.getAfter().getEvent_id())
                .name(messageCDC.getAfter().getName())
                .description(messageCDC.getAfter().getDescription())
                .venueLocation(venueResponse.location())
                .venueName(venueResponse.name())
                .categoryName(category.getName())
                .build();
    }
}
