package com.asheef.common_model_mdb.model.employee;

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
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

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
