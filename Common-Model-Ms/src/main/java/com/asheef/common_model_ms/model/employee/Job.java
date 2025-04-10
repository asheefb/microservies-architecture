package com.asheef.common_model_ms.model.employee;

import com.asheef.common_model_ms.enums.EmployeeType;
import com.asheef.common_model_ms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    private String designation;

    private Date joiningDate;

    @Column(name = "employment_type")
    private String employmentType;

    private Status status;
}
