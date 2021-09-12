package ru.nurmukhametov.cachinggeocoder.controller.dto;

import java.io.Serializable;
import java.util.regex.Pattern;

public enum QueryType implements Serializable {
    ADDRESS,
    COORDINATES;

    public static QueryType getQueryType(String query) {
        if (isCoordinates(query)) {
            return COORDINATES;
        } else {
            return ADDRESS;
        }
    }

    private static boolean isCoordinates(String addressOrCoordinates) {
        return Pattern.matches("[0-9]{1,2}(\\.[0-9]*)?[\\,\\;\\s]+[0-9]{1,2}(\\.[0-9]*)?", addressOrCoordinates);
    }
}

