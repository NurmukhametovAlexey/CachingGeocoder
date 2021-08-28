package ru.nurmukhametov.geocodingcacher.service;

import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

public interface CacheSearch {
    public Geocode search(String addressOrCoordinates);
}
