package ru.nurmukhametov.cachinggeocoder.controller.dto;

import lombok.Data;

@Data
public class Query {
    private String text;
    private QueryType queryType;

    public Query(String text) {
        this.text = text;
        this.queryType = QueryType.getQueryType(text);
    }
}
