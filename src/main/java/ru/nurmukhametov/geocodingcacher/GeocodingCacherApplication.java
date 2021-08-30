    package ru.nurmukhametov.geocodingcacher;

    import lombok.Data;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.ApplicationContext;
    import ru.nurmukhametov.geocodingcacher.model.Geocode;
    import ru.nurmukhametov.geocodingcacher.service.OuterGeocoder;

    @SpringBootApplication
@Data
public class GeocodingCacherApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GeocodingCacherApplication.class, args);

        /*CacheSearchImpl cacheSearch = (CacheSearchImpl) ctx.getBean(CacheSearch.class);

        System.out.println(cacheSearch.search("world").toString());

        System.out.println(cacheSearch.search("hello").toString());

        System.out.println(cacheSearch.search("nope"));*/

        OuterGeocoder yandexOuterGeocoder = ctx.getBean(OuterGeocoder.class);

        //Geocode geocode = yandexOuterGeocoder.makeHttpRequest("Москва");
        Geocode geocode = yandexOuterGeocoder.makeHttpRequest("37.587614 55.753083");

        System.out.println(geocode.getAddress());
    }
}
