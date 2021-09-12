package ru.nurmukhametov.cachinggeocoder.controller.dto;

import lombok.Data;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;

@Data
public class GeocodeResponse {
    private Query query;
    private String coordinates;
    private String fullAddress;

    public GeocodeResponse(Query query, Geocode geocode) {
        this.query = query;
        this.coordinates = geocode.getCoordinates();
        this.fullAddress = geocode.getFullAddress();
    }
}
