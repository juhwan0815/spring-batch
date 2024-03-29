package com.study.springbatch.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {

    private Long id;

    private String name;

    private int price;

    private String type;


}
