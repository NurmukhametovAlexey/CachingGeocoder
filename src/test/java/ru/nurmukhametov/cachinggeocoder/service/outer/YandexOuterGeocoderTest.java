package ru.nurmukhametov.cachinggeocoder.service.outer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderRequestException;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class YandexOuterGeocoderTest {

    private final String yandexApiKey;
    private final String queryPattern;
    private final JsonNode yandexResponse;

    private final String fullAddressInResponse = "Москва, улица Новый Арбат, 24";
    private final String fixedCoordinatesInResponse = "55.753083 37.587614";

    public YandexOuterGeocoderTest() throws IOException {
        Properties testProperties = new Properties();
        try (InputStream input = new FileInputStream(
                getClass().getResource("/test.properties").getFile()
        )) {
            testProperties.load(input);
            yandexApiKey = testProperties.getProperty("yandex.api.geocode.key");
            queryPattern = testProperties.getProperty("yandex.api.geocode.query");
        }

        String testDataPath = getClass().getResource("/yandex_test_response.json").getFile();
        File testJsonFile = new File(testDataPath);
        yandexResponse = new ObjectMapper().readTree(testJsonFile);
    }

    @Mock
    private RestTemplate restTemplate;
    private RestTemplateBuilder mockRestTemplateBuilder = new RestTemplateBuilder() {
        @Override
        public RestTemplate build() {
            return restTemplate;
        }
    };

    private YandexOuterGeocoder underTest;

    @BeforeEach
    void setup(){
        underTest = new YandexOuterGeocoder(mockRestTemplateBuilder, yandexApiKey, queryPattern);
    }

    @Test
    void makeHttpRequestByAddress() throws BadGeocoderRequestException, BadGeocoderResponseException {
        //given
        String address = "address";
        String queryUrl = String.format(queryPattern, yandexApiKey, address);

        Geocode expectedGeocode = new Geocode();
        expectedGeocode.setSearchedAddress(address);
        expectedGeocode.setFullAddress(fullAddressInResponse);
        expectedGeocode.setCoordinates(fixedCoordinatesInResponse);

        BDDMockito.given(restTemplate.getForObject(queryUrl, JsonNode.class)).willReturn(yandexResponse);
        //when
        Geocode resultGeocode = underTest.makeRequestByAddress(address);
        //then
        Mockito.verify(restTemplate,Mockito.times(1)).getForObject(queryUrl, JsonNode.class);
        assertThat(resultGeocode).usingRecursiveComparison().isEqualTo(expectedGeocode);
    }

    @Test
    void makeHttpRequestByCoordinates() throws BadGeocoderRequestException, BadGeocoderResponseException {
        //given
        String nonFixedCoordinates = "37.587614 55.753083";
        String fixedCoordinates = "55.753083 37.587614";
        String queryUrl = String.format(queryPattern, yandexApiKey, fixedCoordinates);

        Geocode expectedGeocode = new Geocode();
        expectedGeocode.setSearchedAddress(fullAddressInResponse);
        expectedGeocode.setFullAddress(fullAddressInResponse);
        expectedGeocode.setCoordinates(fixedCoordinatesInResponse);

        BDDMockito.given(restTemplate.getForObject(queryUrl, JsonNode.class)).willReturn(yandexResponse);
        //when
        Geocode resultGeocode = underTest.makeRequestByCoordinates(nonFixedCoordinates);
        //then
        Mockito.verify(restTemplate,Mockito.times(1)).getForObject(queryUrl, JsonNode.class);
        assertThat(resultGeocode).usingRecursiveComparison().isEqualTo(expectedGeocode);
    }

    @Test
    void executeHttpQuery_IfRestClientExceptionShouldThrowBadGeocoderRequestException() {
        //given
        String query = "query";
        String queryUrl = String.format(queryPattern, yandexApiKey, query);

        BDDMockito.given(restTemplate.getForObject(queryUrl, JsonNode.class)).willThrow(RestClientException.class);
        //when
        assertThatThrownBy(() -> underTest.makeRequestByAddress(query))
                .isInstanceOf(BadGeocoderRequestException.class);
        //then
        Mockito.verify(restTemplate,Mockito.times(1)).getForObject(queryUrl, JsonNode.class);
    }

    @Test
    void parseYandexResponse_IfNullJsonResponseShouldThrowBadGeocoderResponseException() {
        //given
        String query = "query";
        String queryUrl = String.format(queryPattern, yandexApiKey, query);

        BDDMockito.given(restTemplate.getForObject(queryUrl, JsonNode.class)).willReturn(new ObjectMapper().nullNode());
        //when
        assertThatThrownBy(() -> underTest.makeRequestByAddress(query))
                .isInstanceOf(BadGeocoderResponseException.class);
        //then
        Mockito.verify(restTemplate,Mockito.times(1)).getForObject(queryUrl, JsonNode.class);
    }

    @Test
    void fixYandexCoordinates_ifInvalidCoordinatesShouldThrowBadGeocoderRequestException() {
        //given
        String invalidCoordinates = "10.10 20.20 30.30";
        //when
        assertThatThrownBy(() -> underTest.makeRequestByCoordinates(invalidCoordinates))
                .isInstanceOf(BadGeocoderRequestException.class);
        //then
        Mockito.verify(restTemplate,Mockito.never()).getForObject(Mockito.anyString(), Mockito.eq(JsonNode.class));
    }
}