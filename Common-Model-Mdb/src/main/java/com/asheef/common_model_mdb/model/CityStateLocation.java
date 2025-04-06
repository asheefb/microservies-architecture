package com.asheef.common_model_mdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("city_state_location")
public class CityStateLocation {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(value = "erp_id")
    private Integer erpId;

    @Field(value = "country_name")
    private String countryName;

    @Field(value = "country_code")
    private String countryCode;

    @Field(value = "state_name")
    private String stateName;

    @Field(value = "state_iso_code")
    private String stateIsoCode;

    @Field(value = "city_name")
    private String cityName;

    @Field(value = "time_zone")
    private String timeZone;

    private String type;
}
