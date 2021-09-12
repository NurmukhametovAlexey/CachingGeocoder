package ru.nurmukhametov.cachinggeocoder.service.outer;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderRequestException;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;

import java.util.regex.Pattern;

@Service
@PropertySource("classpath:application.properties")
public class YandexOuterGeocoder implements OuterGeocoder {

    private final Logger logger = LoggerFactory.getLogger(YandexOuterGeocoder.class);

    private final RestTemplate restTemplate;

    private final String yandexApiKey;

    private final String queryPattern;

    @Autowired
    public YandexOuterGeocoder(RestTemplateBuilder restTemplateBuilder,
                               @Value("${yandex.api.geocode.key}") String yandexApiKey,
                               @Value("${yandex.api.geocode.query}") String queryPattern) {
         restTemplate = restTemplateBuilder.build();
         this.yandexApiKey = yandexApiKey;
         this.queryPattern = queryPattern;
    }

    @Override
    public Geocode makeRequestByAddress(String address)
            throws BadGeocoderRequestException, BadGeocoderResponseException {
        logger.debug("makeHttpRequestByAddress, address: {}", address);

        JsonNode yandexResponse = executeHttpQuery(address);
        Geocode geocode = parseYandexResponse(yandexResponse);
        geocode.setSearchedAddress(address);

        logger.debug("returning geocode: {}", geocode.toString());

        return geocode;
    }

    @Override
    public Geocode makeRequestByCoordinates(String coordinates)
            throws BadGeocoderRequestException, BadGeocoderResponseException {
        coordinates = fixYandexCoordinates(coordinates);

        logger.debug("makeHttpRequestByAddress, address: {}", coordinates);

        JsonNode yandexResponse = executeHttpQuery(coordinates);
        Geocode geocode = parseYandexResponse(yandexResponse);
        geocode.setSearchedAddress(geocode.getFullAddress());
        geocode.setCoordinates(coordinates);

        logger.debug("returning geocode: {}", geocode.toString());

        return geocode;
    }

    // Executing http query using apiKey
    private JsonNode executeHttpQuery(String query) throws BadGeocoderRequestException {

        String queryUrl = String.format(queryPattern, yandexApiKey, query);
        final JsonNode yandexResponse;
        try {
            yandexResponse = restTemplate.getForObject(queryUrl, JsonNode.class);
        } catch (RestClientException e) {
            logger.error("Exception occurred: {}", e.getClass().getName());
            BadGeocoderRequestException exception = new BadGeocoderRequestException("Error in geocoder request", e);
            throw exception;
        }

        logger.debug("executeHttpQuery. yandexResponse: {}", yandexResponse);

        return yandexResponse;
    }

    // Function for parsing Yandex geocoder json-response.
    // For detailed information check Yandex documentation:
    // https://yandex.ru/dev/maps/geocoder/doc/desc/reference/response_structure.html
    private Geocode parseYandexResponse(JsonNode response) throws BadGeocoderResponseException {
        try {
            JsonNode geocodeInformationNode = response
                    .path("response").path("GeoObjectCollection").path("featureMember").get(0).path("GeoObject");

            String fullAddress = geocodeInformationNode
                    .path("metaDataProperty").path("GeocoderMetaData").path("Address").path("formatted").asText();

            String coordinates = geocodeInformationNode
                    .path("Point").path("pos").asText();

            coordinates = fixYandexCoordinates(coordinates);

            Geocode geocode = new Geocode();
            geocode.setCoordinates(coordinates);
            geocode.setFullAddress(fullAddress);

            logger.debug("parseYandexResponse. Parsed geocode: {}", geocode.toString());

            return geocode;
        } catch (NullPointerException e) {
            logger.error("NullPointerException occurred for response: {}", response);
            throw new BadGeocoderResponseException("Yandex Geocoder API returned empty response", e);
        } catch (BadGeocoderRequestException e) {
            logger.error("BadGeocoderRequestException occurred for response: {}", response);
            throw new BadGeocoderResponseException("Yandex Geocoder API returned response with invalid coordinates", e);
        }
    }

    // For some reason Yandex API returns coordinates in the wrong order
    // This function puts them in standard sequence latitude->longitude
    private String fixYandexCoordinates(String coordinates) throws BadGeocoderRequestException {
        Pattern separator = Pattern.compile("[\\,\\;\\s]+");
        String[] separateCoordinates = separator.split(coordinates);
        if (separateCoordinates.length != 2) {
            logger.error("BadGeocoderRequestException occurred for coordinates: {}", coordinates);
            throw new BadGeocoderRequestException(
                    "Error attempting to fix Yandex API coordinates. Expected 2 coordinates, got: "
                            + separateCoordinates.length);
        }
        logger.debug("fixYandexCoordinates, split coordinates: {} and {}", separateCoordinates[0], separateCoordinates[1]);
        return separateCoordinates[1] + " " + separateCoordinates[0];
    }
}
