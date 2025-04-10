package com.asheef.common_model_ms.model.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_id", unique = true)
    private Integer departmentId;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @Column(name = "department_code", nullable = false, unique = true)
    private String departmentCode;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_by")
    private Long createdBy; // Assuming createdBy references user ID (can use @ManyToOne if needed)

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
