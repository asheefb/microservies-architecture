package com.asheef.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalDetailsDto {

    private String departmentId;

    private String designation;

    private String joiningDate;

    private String employmentType;

    private String status;

    private List<String> skills;

    private String experienceYears;

    private List<String > certifications;

    private String salary;

    private String salaryType;

    private String bankAccountNumber;

    private String ifscCode;

    private String pfNumber;

}
