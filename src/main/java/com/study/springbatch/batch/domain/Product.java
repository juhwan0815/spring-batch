package com.study.springbatch.batch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    private Long id;

    private String name;

    private int price;

    private String type;

    public Product(Long id, String name, int price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }
}
