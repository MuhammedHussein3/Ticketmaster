package com.ticketmaster.search.repo;


import org.springframework.data.elasticsearch.core.query.Criteria;


import java.time.LocalDateTime;
import java.util.List;


public class SearchQueryBuilder {

    private final Criteria criteriaBuilder = new Criteria();


    public Criteria build() {
        return this.criteriaBuilder;
    }

    public SearchQueryBuilder withEventName(String eventName) {
        if (isNotBlank(eventName)) {
            criteriaBuilder.and(Criteria.where("name").is(eventName));
        }
        return this;
    }

    public SearchQueryBuilder withDescription(String description) {
        if (isNotBlank(description)) {
            criteriaBuilder.and(Criteria.where("description").is(description));
        }
        return this;
    }

    public SearchQueryBuilder withVenueName(String venueName) {
        if (isNotBlank(venueName)) {
            criteriaBuilder.and(Criteria.where("venueName").is(venueName));
        }
        return this;
    }

    public SearchQueryBuilder withLocation(String venueLocation) {
        if (isNotBlank(venueLocation)) {
            criteriaBuilder.and(Criteria.where("venueLocation").is(venueLocation));
        }
        return this;
    }

    public SearchQueryBuilder withDate(String dateFrom, String dateTo) {
        if (isNotBlank(dateFrom) && isNotBlank(dateTo) && areDatesWithinBounds(dateFrom, dateTo)) {
            criteriaBuilder.and(Criteria.where("date").between(dateFrom, dateTo));
        }
        return this;
    }

    public SearchQueryBuilder withCategory(String categoryName) {
        if (isNotBlank(categoryName)) {
            criteriaBuilder.and(Criteria.where("categoryName").is(categoryName));
        }
        return this;
    }

    public SearchQueryBuilder withKeywords(List<String> keywords) {
        if (keywords != null && !keywords.isEmpty()) {
            keywords.forEach(keyword -> criteriaBuilder.and(Criteria.where("keywords").contains(keyword)));
        }
        return this;
    }


    private boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private boolean areDatesWithinBounds(String dateFrom, String dateTo){
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime fromDate = LocalDateTime.parse(dateFrom);
        LocalDateTime toDate = LocalDateTime.parse(dateTo);

        return !fromDate.isBefore(today) && !toDate.isAfter(today.plusYears(1));
    }


}
