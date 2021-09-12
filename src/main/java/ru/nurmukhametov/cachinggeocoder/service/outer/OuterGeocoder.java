package ru.nurmukhametov.cachinggeocoder.service.outer;

import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderRequestException;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;

public interface OuterGeocoder {
    public Geocode makeRequestByAddress(String address) throws BadGeocoderRequestException, BadGeocoderResponseException;
    public Geocode makeRequestByCoordinates(String coordinates) throws BadGeocoderRequestException, BadGeocoderResponseException;
}
