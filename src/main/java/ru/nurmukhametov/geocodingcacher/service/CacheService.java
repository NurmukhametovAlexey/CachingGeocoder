package ru.nurmukhametov.geocodingcacher.service;

import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

public interface CacheService {
    public Geocode cacheSearch(String addressOrCoordinates);
    public Geocode cacheSave(Geocode geocode) throws DatabaseException;
}
