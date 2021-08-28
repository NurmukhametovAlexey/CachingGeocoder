    package ru.nurmukhametov.geocodingcacher;

import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.service.CacheSearch;
import ru.nurmukhametov.geocodingcacher.service.CacheSearchImpl;
import ru.nurmukhametov.geocodingcacher.service.GeocodeService;

//@SpringBootApplication
@ComponentScan("ru.nurmukhametov.geocodingcacher.config")
@Data
public class GeocodingCacherApplication {
    public static void main(String[] args) {
        //SpringApplication.run(GeocodingCacherApplication.class, args);
        ApplicationContext ctx = new AnnotationConfigApplicationContext(GeocodingCacherApplication.class);
        /*GeocodeService geocodeService = ctx.getBean(GeocodeService.class);
        Geocode geocode = new Geocode("hello", "world");
        System.out.println(geocodeService.saveGeocode(geocode));*/

        CacheSearchImpl cacheSearch = (CacheSearchImpl) ctx.getBean(CacheSearch.class);

        System.out.println(cacheSearch.search("world").toString());

        System.out.println(cacheSearch.search("hello").toString());

        System.out.println(cacheSearch.search("nope"));
    }
}
