package com.asheef.common_model_mdb.model.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressInformation {

    @Field(value = "address_line_1")
    private String addressLine1;

    @Field(value = "address_line_2")
    private String addressLine2;

    @Field(targetType = FieldType.OBJECT_ID)
    private String city;

    @Field(targetType = FieldType.OBJECT_ID)
    private String state;

    @Field(targetType = FieldType.OBJECT_ID)
    private String country;

    private Integer pinCode;
}
