package com.asheef.common_model_mdb.model.response;

import lombok.Data;

@Data
public class UserResponseModel {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String cityName;

    private String stateName;

    private String countryName;

    private String pinCode;
}
