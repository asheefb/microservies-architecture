package com.asheef.common_model_mdb.model.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobModel {

    @Field(value = "department_id")
    private String departmentId;

    private String designation;

    @Field(value = "joining_date")
    private Date joiningDate;

    @Field(value = "employment_type")
    private String  employmentType;

    private String status;
}
