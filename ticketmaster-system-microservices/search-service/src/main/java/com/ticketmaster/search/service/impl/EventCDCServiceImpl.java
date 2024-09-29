package com.ticketmaster.search.service.impl;


import com.ticketmaster.search.client.CategoryClient;
import com.ticketmaster.search.client.VenueClient;
import com.ticketmaster.search.dto.VenueResponse;
import com.ticketmaster.search.entity.SearchEvent;
import com.ticketmaster.search.exceptions.EventNotFoundException;
import com.ticketmaster.search.mapper.SearchMapper;
import com.ticketmaster.search.message.After;
import com.ticketmaster.search.message.Before;
import com.ticketmaster.search.message.Category;
import com.ticketmaster.search.message.EventMessageCDC;
import com.ticketmaster.search.repo.SearchRepository;
import com.ticketmaster.search.service.EventCDCService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventCDCServiceImpl implements EventCDCService {


    private final SearchRepository searchRepository;
    private final SearchMapper searchMapper;
    private final KeywordExtractorImpl keywordExtractor;
    private final CategoryClient categoryClient;
    private final VenueClient venueClient;

    @Transactional
    @Override
    public void createEvent(EventMessageCDC messageCDC) {


        Category getCategory = getCategory(messageCDC.getAfter().getCategory_id());
        log.info("Successfully fetched category with ID: {}", messageCDC.getAfter().getCategory_id());
        assert getCategory != null;
        log.info("Category name: {}", getCategory.getName());


        VenueResponse getVenue = getVenue(messageCDC.getAfter().getVenue_id());
        log.info("Successfully fetched venue with ID: {}", messageCDC.getAfter().getVenue_id());
        assert getVenue != null;
        log.info("Venue name: {}, Venue location: {}", getVenue.name(), getVenue.location());



        SearchEvent searchEvent = mapToCreateSearchEvent(messageCDC, getCategory, getVenue);


        long startTimeNano = Long.parseLong(messageCDC.getAfter().getStart_time());

        long startTimeMillis = Long.parseLong(messageCDC.getAfter().getStart_time()) / 1_000_000;
        Instant date = Instant.ofEpochMilli(startTimeMillis);

        DateTimeFormatter formatter = DateTimeFormatter

                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneId.of("UTC"));

        String formattedDate = formatter.format(date);

        log.info("Date Converter Time {}", formattedDate);

        searchEvent.setDate(formattedDate);

        searchEvent.setKeywords(
                keywordExtractor.extractKeywordsFromDescription(searchEvent.getDescription())
        );
        log.info("Extracted keywords: {}", Arrays.toString(searchEvent.getKeywords().toArray(new String[0])));


        log.info("Preparing to save SearchEvent with details - Venue Location: {}, Event Date: {}, Description: {}, Category: {}, Keywords: {}",
                searchEvent.getVenueLocation(),
                searchEvent.getDate(),
                searchEvent.getDescription(),
                searchEvent.getCategoryName(),
                searchEvent.getKeywords());



        searchRepository.save(searchEvent);
        log.info("SearchEvent successfully saved with ID: {}", searchEvent.getId());
    }


    private SearchEvent mapToCreateSearchEvent(EventMessageCDC messageCDC, Category category, VenueResponse venueResponse){
        return searchMapper.mapToSearchEvent(messageCDC, category, venueResponse);
    }

    public void updateEvent(EventMessageCDC messageCDC) {

        Long eventId = messageCDC.getBefore().getEvent_id();
        log.info("Updating event with ID: {}", eventId);

        Before beforeUpdate = messageCDC.getBefore();
        After afterUpdated = messageCDC.getAfter();

        SearchEvent searchEvent = searchRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Event not found with ID: {}", eventId);
                    return new EventNotFoundException(String.format("Event not found with ID::%d", eventId));
                });

        log.info("Found event for update: {}", eventId);

        if (!beforeUpdate.getName().equals(afterUpdated.getName())) {
            log.info("Updating event name from '{}' to '{}'", beforeUpdate.getName(), afterUpdated.getName());
            searchEvent.setName(afterUpdated.getName());
        }

        if (!beforeUpdate.getDescription().equals(afterUpdated.getDescription())) {
            log.info("Updating event description from '{}' to '{}'", beforeUpdate.getDescription(), afterUpdated.getDescription());
            searchEvent.setDescription(afterUpdated.getDescription());

            searchEvent.setKeywords(
                    keywordExtractor.extractKeywordsFromDescription(afterUpdated.getDescription())
            );
            log.info("Extracted keywords: {}", Arrays.toString(searchEvent.getKeywords().toArray(new String[0])));
        }

        if (!beforeUpdate.getStart_time().equals(afterUpdated.getStart_time())) {
            log.info("Updating event date from '{}' to '{}'", beforeUpdate.getStart_time(), afterUpdated.getStart_time());
            searchEvent.setDate(afterUpdated.getStart_time());
        }

        if (!Objects.equals(beforeUpdate.getVenue_id(), afterUpdated.getVenue_id())) {
            log.info("Venue ID changed from '{}' to '{}', fetching new venue details", beforeUpdate.getVenue_id(), afterUpdated.getVenue_id());
            VenueResponse venueResponse = getVenue(afterUpdated.getVenue_id());

            log.info("Updating venue name to '{}' and location to '{}'", venueResponse.name(), venueResponse.location());
            searchEvent.setVenueLocation(venueResponse.location());
            searchEvent.setVenueName(venueResponse.name());
        }

        if (!Objects.equals(beforeUpdate.getCategory_id(), afterUpdated.getCategory_id())) {
            log.info("Category ID changed from '{}' to '{}', fetching new category details", beforeUpdate.getCategory_id(), afterUpdated.getCategory_id());
            Category category = getCategory(afterUpdated.getCategory_id());

            log.info("Updating category name to '{}'", category.getName());
            searchEvent.setCategoryName(category.getName());
        }

        searchRepository.save(searchEvent);
        log.info("Successfully updated and saved event with ID: {}", eventId);
    }


    private VenueResponse getVenue(Integer venueId){
        return venueClient.getVenueById(venueId).getBody();
    }

    private Category getCategory(Integer categoryId){
        return categoryClient.getCategoryById(categoryId).getBody();
    }
    @Override
    public void deleteEvent(EventMessageCDC messageCDC) {

        final Long eventId = messageCDC.getAfter().getEvent_id();
        searchRepository.deleteById(eventId);
    }
}
