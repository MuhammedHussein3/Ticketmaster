package com.ticketmaster.search.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(indexName = "event-index")
public class SearchEvent{

    private @Id Long id;
    @Field(type = Text)
    private String name;
    @Field(type = Text)
    private String description;
    @Field(type = Text)
    private String venueName;
    private @Field(type = Date) String date;
    @Field(type = Text)
    private String venueLocation;
    @Field(type = Keyword)
    private String categoryName;
    @Field(type = Keyword)
    private List<String> keywords;
}
