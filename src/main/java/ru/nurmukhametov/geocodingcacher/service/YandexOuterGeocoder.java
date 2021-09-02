package ru.nurmukhametov.geocodingcacher.service;

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
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

import java.util.regex.Pattern;

@Service
@PropertySource("classpath:application.properties")
public class YandexOuterGeocoder implements OuterGeocoder {

    private final Logger logger = LoggerFactory.getLogger(YandexOuterGeocoder.class);

    private RestTemplate restTemplate;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public YandexOuterGeocoder(RestTemplateBuilder restTemplateBuilder) {
         restTemplate = restTemplateBuilder.build();
    }

    @Value("${yandex.api.geocode.key}")
    String yandexApiKey;

    @Value("${yandex.api.geocode.query}")
    String queryPattern;

    @Override
    public Geocode makeHttpRequest (String addressOrCoordinates) throws BadGeocoderRequestException {

        if (isCoordinates(addressOrCoordinates)) {
            logger.debug("{} are coordinates", addressOrCoordinates);
            addressOrCoordinates = fixYandexCoordinates(addressOrCoordinates);
        }

        String queryUrl = String.format(queryPattern, yandexApiKey, addressOrCoordinates);

        logger.debug("queryUrl: {}", queryUrl);

        //queryUrl = "http://localhost:8080/test";

        final JsonNode yandexResponse;
        try {
            yandexResponse = restTemplate.getForObject(queryUrl, JsonNode.class);
        } catch (RestClientException e) {
            logger.error("Exception occurred: {}", e.getClass().getName());
            BadGeocoderRequestException exception = new BadGeocoderRequestException("Error in geocoder request", e);
            throw exception;
        }

        logger.debug("yandexResponse: {}", yandexResponse);

        // Parsing Yandex geocoder json-response.
        // For detailed information check Yandex documentation:
        // https://yandex.ru/dev/maps/geocoder/doc/desc/reference/response_structure.html
        JsonNode geocodeInformation = yandexResponse
                .path("response").path("GeoObjectCollection").path("featureMember").get(0).path("GeoObject");

        String address = geocodeInformation.path("name").asText();
        String coordinates = geocodeInformation.path("Point").path("pos").asText();
        coordinates = fixYandexCoordinates(coordinates);

        logger.debug("returning parsed geocode: address=<<{}>>, coordinates=<<{}>>", address, coordinates);

        Geocode geocode = new Geocode();
        geocode.setCoordinates(coordinates);
        geocode.setAddress(address);
        return geocode;
    }

    private boolean isCoordinates(String addressOrCoordinates) {
        return Pattern.matches("[0-9]{1,2}(\\.[0-9]*)?[\\,\\;\\s]+[0-9]{1,2}(\\.[0-9]*)?", addressOrCoordinates);
    }

    // For some reason Yandex API returns coordinates in the wrong order
    // This function puts them in standard sequence latitude->longitude
    private String fixYandexCoordinates(String coordinates) {
        Pattern separator = Pattern.compile("[\\,\\;\\s]+");
        String[] separateCoordinates = separator.split(coordinates, 2);
        logger.debug("fixYandexCoordinates, split coordinates: {} and {}", separateCoordinates[0], separateCoordinates[1]);
        return separateCoordinates[1] + " " + separateCoordinates[0];
    }
}
