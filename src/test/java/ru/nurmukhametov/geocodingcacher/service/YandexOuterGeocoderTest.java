package ru.nurmukhametov.geocodingcacher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class )
@SpringBootTest
@PropertySource("classpath:application.properties")
class YandexOuterGeocoderTest {

    @Value("${yandex.api.geocode.key}")
    String yandexApiKey;

    @Value("${yandex.api.geocode.query}")
    String queryPattern;

    @Mock private RestTemplate restTemplate;

    @Autowired
    private YandexOuterGeocoder yandexOuterGeocoder;

    @BeforeEach
    void setup(){
        yandexOuterGeocoder.setRestTemplate(restTemplate);
    }

    @Test
    void makeHttpRequest() {
        //given
        String addressOrCoordinates = "aoc";
        String queryUrl = String.format(queryPattern, yandexApiKey, addressOrCoordinates);
        //when
        yandexOuterGeocoder.makeHttpRequest(addressOrCoordinates);
        //then
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(queryUrl, String.class);
    }
}