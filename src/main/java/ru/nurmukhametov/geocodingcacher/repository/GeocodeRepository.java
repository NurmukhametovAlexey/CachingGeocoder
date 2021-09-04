package ru.nurmukhametov.geocodingcacher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

import java.util.List;

@Repository
public interface GeocodeRepository extends CrudRepository<Geocode, String> {
    Geocode findBySearchedAddress(String address);
    List<Geocode> findByFullAddress(String address);
    Geocode findByCoordinates(String coordinates);
}
