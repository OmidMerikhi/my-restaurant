package com.omid.employeeservice.chef;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Chef {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private Integer experienceYear;
    private String about;
    private Long serviceDate;
    private Boolean active;

}
