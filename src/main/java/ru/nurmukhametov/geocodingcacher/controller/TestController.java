package ru.nurmukhametov.geocodingcacher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    String response() {
        return  "{\n" +
                "  \"response\": {\n" +
                "    \"GeoObjectCollection\": {\n" +
                "      \"metaDataProperty\": {\n" +
                "        \"GeocoderResponseMetaData\": {\n" +
                "          \"request\": \"Москва, улица Новый Арбат, дом 24\",\n" +
                "          \"found\": \"1\",\n" +
                "          \"results\": \"10\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"featureMember\": [\n" +
                "        {\n" +
                "          \"GeoObject\": {\n" +
                "            \"metaDataProperty\": {\n" +
                "              \"GeocoderMetaData\": {\n" +
                "                \"kind\": \"house\",\n" +
                "                \"text\": \"Россия, Москва, улица Новый Арбат, 24\",\n" +
                "                \"precision\": \"exact\",\n" +
                "                \"Address\": {\n" +
                "                  \"country_code\": \"RU\",\n" +
                "                  \"postal_code\": \"119019\",\n" +
                "                  \"formatted\": \"Москва, улица Новый Арбат, 24\",\n" +
                "                  \"Components\": [\n" +
                "                    {\n" +
                "                      \"kind\": \"country\",\n" +
                "                      \"name\": \"Россия\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"kind\": \"province\",\n" +
                "                      \"name\": \"Центральный федеральный округ\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"kind\": \"province\",\n" +
                "                      \"name\": \"Москва\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"kind\": \"locality\",\n" +
                "                      \"name\": \"Москва\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"kind\": \"street\",\n" +
                "                      \"name\": \"улица Новый Арбат\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"kind\": \"house\",\n" +
                "                      \"name\": \"24\"\n" +
                "                    }\n" +
                "                  ]\n" +
                "                },\n" +
                "                \"AddressDetails\": {\n" +
                "                  \"Country\": {\n" +
                "                    \"AddressLine\": \"Москва, улица Новый Арбат, 24\",\n" +
                "                    \"CountryNameCode\": \"RU\",\n" +
                "                    \"CountryName\": \"Россия\",\n" +
                "                    \"AdministrativeArea\": {\n" +
                "                      \"AdministrativeAreaName\": \"Москва\",\n" +
                "                      \"Locality\": {\n" +
                "                        \"LocalityName\": \"Москва\",\n" +
                "                        \"Thoroughfare\": {\n" +
                "                          \"ThoroughfareName\": \"улица Новый Арбат\",\n" +
                "                          \"Premise\": {\n" +
                "                            \"PremiseNumber\": \"24\",\n" +
                "                            \"PostalCode\": {\n" +
                "                              \"PostalCodeNumber\": \"119019\"\n" +
                "                            }\n" +
                "                          }\n" +
                "                        }\n" +
                "                      }\n" +
                "                    }\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            \"description\": \"Москва, Россия\",\n" +
                "            \"name\": \"улица Новый Арбат, 24\",\n" +
                "            \"boundedBy\": {\n" +
                "              \"Envelope\": {\n" +
                "                \"lowerCorner\": \"37.583508 55.750768\",\n" +
                "                \"upperCorner\": \"37.591719 55.755398\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"Point\": {\n" +
                "              \"pos\": \"37.587614 55.753083\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }
}
