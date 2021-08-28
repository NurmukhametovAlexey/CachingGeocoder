    package ru.nurmukhametov.geocodingcasher;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.nurmukhametov.geocodingcasher.model.Geocode;
import ru.nurmukhametov.geocodingcasher.service.GeocodeService;

//@SpringBootApplication
@ComponentScan("ru.nurmukhametov.geocodingcasher.config")
@Data
public class GeocodingCasherApplication {
    public static void main(String[] args) {
        //SpringApplication.run(GeocodingCasherApplication.class, args);
        ApplicationContext ctx = new AnnotationConfigApplicationContext(GeocodingCasherApplication.class);
        GeocodeService geocodeService = ctx.getBean(GeocodeService.class);
        Geocode geocode = new Geocode("hello", "world");
        System.out.println(geocodeService.saveGeocode(geocode));

        System.out.println(geocodeService.getAddressByCoordinates("hello"));

        System.out.println(geocodeService.getCoordinatesByAddress("world"));

        System.out.println(geocodeService.getCoordinatesByAddress("nope"));
    }
}
