package com.ticketmaster.search.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {
    @JsonProperty("version")
    private String version;

    @JsonProperty("connector")
    private String connector;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ts_ms")
    private Long ts_ms;

    @JsonProperty("snapshot")
    private String snapshot;

    @JsonProperty("db")
    private String db;

    @JsonProperty("sequence")
    private String sequence;

    @JsonProperty("schema")
    private String schema;

    @JsonProperty("table")
    private String table;

    @JsonProperty("txId")
    private int txId;

    @JsonProperty("lsn")
    private int lsn;

    @JsonProperty("xmin")
    private Object xmin;
}

