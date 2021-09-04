package ru.nurmukhametov.geocodingcacher.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "geocode")
@IdClass(GeocodeID.class)
@Data
public class Geocode implements Serializable {
    @Id
    private String coordinates;

    @Id
    @Column(name = "searched_address")
    private String searchedAddress;

    @Column(name = "full_address")
    private String fullAddress;
}

