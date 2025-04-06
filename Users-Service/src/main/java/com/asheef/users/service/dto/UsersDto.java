package com.asheef.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date dateOfBirth;

    private String gender;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String pinCode;

}
