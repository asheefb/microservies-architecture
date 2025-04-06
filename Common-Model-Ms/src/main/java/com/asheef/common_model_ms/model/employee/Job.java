package com.asheef.common_model_ms.model.employee;

import com.asheef.common_model_ms.enums.EmployeeType;
import com.asheef.common_model_ms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String departmentId;

    private String designation;

    private Date joiningDate;

    private EmployeeType employmentType;

    private Status status;
}
