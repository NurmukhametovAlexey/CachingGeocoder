package ru.nurmukhametov.geocodingcacher.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.service.outer.YandexOuterGeocoder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
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

    @Disabled
    @Test
    void makeHttpRequest() throws IOException, BadGeocoderRequestException {
        //given
        String addressOrCoordinates = "aoc";
        String queryUrl = String.format(queryPattern, yandexApiKey, addressOrCoordinates);
        Geocode expectedResponse = new Geocode();
        expectedResponse.setCoordinates("37.587614 55.753083");
        expectedResponse.setSearchedAddress("улица Новый Арбат, 24");

        String testDataPath = getClass().getResource("/yandex_test_response.json").getFile();
        File file = new File(testDataPath);
        BDDMockito.given(restTemplate.getForObject(queryUrl, JsonNode.class)).willReturn(
                new ObjectMapper().readTree(file)
        );

        //when
        //Geocode methodResponse = yandexOuterGeocoder.makeHttpRequest(addressOrCoordinates);

        //then
        //Mockito.verify(restTemplate, Mockito.times(1)).getForObject(queryUrl, JsonNode.class);

        //assertThat(methodResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}