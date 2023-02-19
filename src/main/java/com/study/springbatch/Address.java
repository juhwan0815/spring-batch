package com.study.springbatch;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
