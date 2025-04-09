package com.asheef.common_model_mdb.model.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.Column;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalDetails {

    @Field(value = "job_details")
    private JobModel jobDetails;

    private List<String> skills;

    @Field(value = "education_details")
    private EducationDetails educationDetails;

    @Field(value = "experience_years")
    private Integer experienceYears;

    private List<String> certifications;

    @Field(value = "salary_details")
    private Salary salaryDetails;
}
