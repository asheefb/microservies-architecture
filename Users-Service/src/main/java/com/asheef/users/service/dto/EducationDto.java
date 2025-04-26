package com.asheef.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {

    private String id;

    private String educationLevel;

    private String boardName;

    private String university;

    private String schoolOrCollegeName;

    private String location;

    private String passOut;

    private String percentage;

    private String passingYear;

    private String grade;

    private String medium;

    private String stream;
}
