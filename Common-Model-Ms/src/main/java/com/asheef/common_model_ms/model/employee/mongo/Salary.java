package com.asheef.common_model_ms.model.employee.mongo;

import com.asheef.common_model_ms.enums.SalaryType;
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
@Document(value = "salary")
public class Salary {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    private Long salary;

    @Field(value = "salary_type")
    private SalaryType salaryType;

    @Field(value = "bank_account_number")
    private String bankAccountNumber;

    @Field(value = "ifsc_code")
    private String ifscCode;

    @Field(value = "pf_number")
    private String pfNumber;
}
