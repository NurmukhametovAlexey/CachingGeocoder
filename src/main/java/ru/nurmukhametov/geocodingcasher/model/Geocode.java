package ru.nurmukhametov.geocodingcasher.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "geocode")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Geocode {
    @Id
    private String coordinates;

    private String address;
}
