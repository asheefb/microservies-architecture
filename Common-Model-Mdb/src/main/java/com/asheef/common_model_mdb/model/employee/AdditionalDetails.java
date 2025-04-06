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

    @Field(targetType = FieldType.OBJECT_ID,value = "job_id")
    private String jobId;

    private List<String> skills;

    private String education;

    @Column(name = "experience_years")
    private Integer experienceYears;

    private List<String> certifications;

    private Double salary;

    @Field(value = "salary_type")
    private String salaryType;

    @Field(value = "bank_account_number")
    private String bankAccountNumber;

    @Field(value = "ifsc_code")
    private String ifscCode;

    @Field(value = "pf_number")
    private String pfNumber;
}
