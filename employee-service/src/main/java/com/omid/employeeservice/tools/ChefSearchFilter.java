package com.omid.employeeservice.tools;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChefSearchFilter {
    private String firstName;
    private String lastName;
    private String nationalCode;
    private Integer experienceYear;
    private String about;
    private LocalDate serviceDate;
    private Boolean active;
}
