package ru.nurmukhametov.geocodingcacher.service;

import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

public interface OuterGeocoder {
    public Geocode makeHttpRequest(String addressOrCoordinates) throws BadGeocoderRequestException;
}
