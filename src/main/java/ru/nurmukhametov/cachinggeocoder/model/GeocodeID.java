package ru.nurmukhametov.cachinggeocoder.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeocodeID implements Serializable {
    private String coordinates;
    private String searchedAddress;
}
