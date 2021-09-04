package ru.nurmukhametov.geocodingcacher.service.outer;

import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

public interface OuterGeocoder {
    public Geocode makeHttpRequestByAddress(String address) throws BadGeocoderRequestException;
    public Geocode makeHttpRequestByCoordinates(String coordinates) throws BadGeocoderRequestException;
}
