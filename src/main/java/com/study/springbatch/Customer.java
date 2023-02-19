package com.study.springbatch;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private int age;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private Address address;

}
