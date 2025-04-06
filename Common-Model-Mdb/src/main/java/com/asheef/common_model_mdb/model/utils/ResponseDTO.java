package com.asheef.common_model_mdb.model.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Boolean success;

    private Object data;

    private Integer statusCode;

    private String message;
}
