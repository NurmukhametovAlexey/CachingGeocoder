package ru.nurmukhametov.geocodingcacher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

@Service
@PropertySource("classpath:application.properties")
public class YandexOuterGeocoder implements OuterGeocoder {

    private final Logger logger = LoggerFactory.getLogger(YandexOuterGeocoder.class);

    private final RestTemplate restTemplate;

    @Autowired
    public YandexOuterGeocoder(RestTemplateBuilder restTemplateBuilder) {
         restTemplate = restTemplateBuilder.build();
    }

    @Value("${yandex.api.geocode.key}")
    String yandexApiKey;

    @Value("${yandex.api.geocode.query}")
    String queryPattern;

    @Override
    public Geocode makeHttpRequest (String addressOrCoordinates) {

        String queryUrl = String.format(queryPattern, yandexApiKey, addressOrCoordinates);

        logger.debug("queryUrl: {}", queryUrl);

        final String object = restTemplate.getForObject(queryUrl, String.class);

        Geocode geocode = new Geocode();
        geocode.setAddress(object);
        geocode.setCoordinates(object);

        return geocode;
    }

}
