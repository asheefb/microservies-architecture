package com.asheef.common_model_ms.model.employee.mongo;

import com.asheef.common_model_ms.enums.EmployeeType;
import com.asheef.common_model_ms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "job")
public class JobModel {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(value = "department_id")
    private String departmentId;

    private String designation;

    @Field(value = "joining_date")
    private Date joiningDate;

    @Field(value = "employment_type")
    private EmployeeType employmentType;

    private Status status;
}
