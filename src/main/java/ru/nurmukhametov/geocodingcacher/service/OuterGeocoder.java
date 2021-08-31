package ru.nurmukhametov.geocodingcacher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

public interface OuterGeocoder {
    public Geocode makeHttpRequest(String addressOrCoordinates) throws JsonProcessingException;
}
