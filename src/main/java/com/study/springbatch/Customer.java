package com.study.springbatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private long id;
    private String firstName;
    private String lastName;
    private Date birthdate;
}
