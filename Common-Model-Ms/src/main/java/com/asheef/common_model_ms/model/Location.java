package com.asheef.common_model_ms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_iso_code")
    private String stateIsoCode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "time_zone")
    private String timeZone;

    private String type;
}
