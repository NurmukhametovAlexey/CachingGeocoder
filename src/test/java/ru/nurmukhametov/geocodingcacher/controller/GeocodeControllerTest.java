package ru.nurmukhametov.geocodingcacher.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nurmukhametov.geocodingcacher.controller.dto.GeocodeResponse;
import ru.nurmukhametov.geocodingcacher.controller.dto.Query;
import ru.nurmukhametov.geocodingcacher.controller.dto.QueryType;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderResponseException;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.service.CachedGeocodingService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class GeocodeControllerTest {

    @Mock
    private CachedGeocodingService geocodingService;

    private GeocodeController underTest;

    @BeforeEach
    void setUp() {
        underTest = new GeocodeController(geocodingService);
    }

    @Test
    void getGeocode_WhenGivenAddress()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String address = "address";
        Query query = new Query(address);
        Geocode geocode = new Geocode();
        geocode.setCoordinates("55 33");
        geocode.setFullAddress("full address");
        GeocodeResponse expectedGeocodeResponse = new GeocodeResponse(query, geocode);
        BDDMockito.given(geocodingService.findGeocode(query)).willReturn(geocode);
        //when
        GeocodeResponse resultGeocodeResponse = underTest.getGeocode(address);
        //then
        assertThat(query.getQueryType()).isEqualTo(QueryType.ADDRESS);
        assertThat(expectedGeocodeResponse).usingRecursiveComparison().isEqualTo(resultGeocodeResponse);
        Mockito.verify(geocodingService, Mockito.times(1)).findGeocode(query);
    }

    @Test
    void getGeocode_WhenGivenCoordinates()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String coordinates = "55 33";
        Query query = new Query(coordinates);
        Geocode geocode = new Geocode();
        geocode.setCoordinates(coordinates);
        geocode.setFullAddress("full address");
        GeocodeResponse expectedGeocodeResponse = new GeocodeResponse(query, geocode);
        BDDMockito.given(geocodingService.findGeocode(query)).willReturn(geocode);
        //when
        GeocodeResponse resultGeocodeResponse = underTest.getGeocode(coordinates);
        //then
        assertThat(query.getQueryType()).isEqualTo(QueryType.COORDINATES);
        assertThat(expectedGeocodeResponse).usingRecursiveComparison().isEqualTo(resultGeocodeResponse);
        Mockito.verify(geocodingService, Mockito.times(1)).findGeocode(query);
    }
}