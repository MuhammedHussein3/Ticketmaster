package com.ticketmaster.search.service;

import java.util.List;

/**
 * Interface for extracting keywords from event descriptions.
 *
 * Implementations of this interface will provide logic to analyze event
 * descriptions and extract meaningful keywords for indexing or search purposes.
 */
public interface KeywordExtractor {


    /**
     * Extracts relevant keywords from the provided event description.
     *
     * @param description the event description to analyze
     * @return a list of extracted keywords based on the description
     */
    List<String> extractKeywordsFromDescription(String description);
}
