package ru.nurmukhametov.geocodingcacher.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "geocode")
@Data
public class Geocode {
    @Id
    private String coordinates;

    private String address;
}

