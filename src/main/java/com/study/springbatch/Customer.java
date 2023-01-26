package com.study.springbatch;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

    private Long id;

    private String username;

    private int age;

    public void setUsername(String username) {
        this.username = username;
    }
}
