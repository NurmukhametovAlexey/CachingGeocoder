package ru.nurmukhametov.geocodingcacher.service.outer;

import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderResponseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

public interface OuterGeocoder {
    public Geocode makeRequestByAddress(String address) throws BadGeocoderRequestException, BadGeocoderResponseException;
    public Geocode makeRequestByCoordinates(String coordinates) throws BadGeocoderRequestException, BadGeocoderResponseException;
}
