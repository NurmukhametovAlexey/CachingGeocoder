    package ru.nurmukhametov.geocodingcacher;

    import com.fasterxml.jackson.core.JsonProcessingException;
    import lombok.Data;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.ApplicationContext;
    import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
    import ru.nurmukhametov.geocodingcacher.model.Geocode;
    import ru.nurmukhametov.geocodingcacher.service.CachedGeocodingService;
    import ru.nurmukhametov.geocodingcacher.service.OuterGeocoder;

    @SpringBootApplication
@Data
public class GeocodingCacherApplication {
    public static void main(String[] args) throws JsonProcessingException {
        ApplicationContext ctx = SpringApplication.run(GeocodingCacherApplication.class, args);


        CachedGeocodingService cachedGeocodingService = ctx.getBean(CachedGeocodingService.class);

        Geocode geocode = null;
        try {
            geocode = cachedGeocodingService.findGeocode("Москва");
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        System.out.println(geocode);
    }
}
