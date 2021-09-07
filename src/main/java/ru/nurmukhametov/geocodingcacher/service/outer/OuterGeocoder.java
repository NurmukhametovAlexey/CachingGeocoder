package ru.nurmukhametov.geocodingcacher.service.outer;

import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.exception.ResultsNotFoundException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

public interface OuterGeocoder {
    public Geocode makeHttpRequestByAddress(String address) throws BadGeocoderRequestException, ResultsNotFoundException;
    public Geocode makeHttpRequestByCoordinates(String coordinates) throws BadGeocoderRequestException, ResultsNotFoundException;
}
