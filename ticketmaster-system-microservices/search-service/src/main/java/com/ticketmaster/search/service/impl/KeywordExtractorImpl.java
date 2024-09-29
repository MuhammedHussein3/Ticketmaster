package com.ticketmaster.search.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeywordExtractorImpl implements com.ticketmaster.search.service.KeywordExtractor {

    public final static Map<String, List<String>> CATEGORY_KEYWORDS = Map.of(
            "match", Arrays.asList("football", "soccer", "match", "game", "tournament", "league", "competition"),
            "concert", Arrays.asList("concert", "music", "band", "live", "performance", "artist", "gig"),
            "theater", Arrays.asList("theater", "play", "drama", "stage", "show", "musical", "production"),
            "party", Arrays.asList("party", "celebration", "gala", "festival", "dance", "dj", "rave", "club")
    );
    // Default category when the event doesn't match any known category
    private static final String DEFAULT_CATEGORY = "other";

    // Default keywords for new or undefined events
    public final static List<String> DEFAULT_KEYWORDS = Arrays.asList("event", "gathering", "festival", "celebration");


    @Override
    public List<String> extractKeywordsFromDescription(String description) {

        if (description == null || description.isBlank()){
            return DEFAULT_KEYWORDS;
        }
        String lowerCaseDescription  = description.toLowerCase(Locale.ROOT);
        List<String> keywords;


        for (Map.Entry<String, List<String>> entry : CATEGORY_KEYWORDS.entrySet()){
            if (lowerCaseDescription.contains(entry.getKey())){
                keywords = entry.getValue();
                return keywords;
            }
        }

        return DEFAULT_KEYWORDS;
    }
}
