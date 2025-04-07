package com.asheef.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CityStateLocationDto {

    private String countryName;

    private String countryCode;

    private String stateName;

    private String stateIsoCode;

    private String cityName;

    private String timeZone;

    private String type;
}
