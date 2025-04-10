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
@Document("department")
public class Department {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field("department_id")
    private Integer departmentId;

    @Field("department_name")
    private String departmentName;

    @Field("department_code")
    private String departmentCode;

    @Field("description")
    private String description;

    @Field("is_active")
    private boolean active;

    @Field("created_by")
    private String createdBy;

    @Field("updated_by")
    private String updatedBy;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;
}
