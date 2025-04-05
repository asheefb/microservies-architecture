package com.asheef.common_model_ms.model.employee.mongo;

import com.asheef.common_model_ms.enums.Gender;
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
@Document(value = "employee")
public class EmployeeModel {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    private Integer mysqlId;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    private String email;

    @Field(name = "phone_number")
    private String phoneNumber;

    @Field(name = "date_of_birth")
    private Date dateOfBirth;

    private Gender gender;

    private JobModel job;

    private AddressInformation addressInformation;

    private Date createdAt;

    private Date updatedAt;

    private Salary salary;

    private AdditionalDetails additionalDetails;

}
