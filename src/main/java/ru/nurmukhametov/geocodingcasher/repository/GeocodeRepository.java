package ru.nurmukhametov.geocodingcasher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nurmukhametov.geocodingcasher.model.Geocode;

@Repository
public interface GeocodeRepository extends CrudRepository<Geocode, String> {
    Geocode findByAddress(String address);
    Geocode findByCoordinates(String coordinates);
}
