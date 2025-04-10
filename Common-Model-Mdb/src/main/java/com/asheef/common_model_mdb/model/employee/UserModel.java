package com.asheef.common_model_mdb.model.employee;

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
@Document(value = "users")
public class UserModel {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(value = "user_id")
    private Integer userId;

    @Field(value = "salary_id")
    private Integer salaryId;

    @Field(value = "first_name")
    private String firstName;

    @Field(value = "last_name")
    private String lastName;

    private String email;

    @Field(value = "phone_number")
    private String phoneNumber;

    @Field(value = "date_of_birth")
    private Date dateOfBirth;

    private String  gender;

    @Field(value = "address_info")
    private AddressInformation addressInformation;

    @Field(value = "is_active")
    private boolean active;

    private String status;

    @Field(value = "created_at")
    private Date createdAt;

    @Field(value = "updated_at")
    private Date updatedAt;

    @Field(value = "additional_details")
    private AdditionalDetails additionalDetails;

    @Field(value = "created_by",targetType = FieldType.OBJECT_ID)
    private String createdBy;

    @Field(value = "updated_by",targetType = FieldType.OBJECT_ID)
    private String updatedBy;

}
