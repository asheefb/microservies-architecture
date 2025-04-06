package com.asheef.common_model_mdb.model.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "users_audit")
public class UsersAudit {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private Integer id;

    @Field(value = "user_id")
    private Integer userId;

    private String from;

    private String to;

    @Field(targetType = FieldType.OBJECT_ID,value = "created_by")
    private String createdBy;

    @Field(targetType = FieldType.OBJECT_ID,value = "updated_by")
    private String updatedBy;

    @Field(value = "created_at")
    private Date createdAt;

    @Field(value = "updated_at")
    private Date updatedAt;

}
