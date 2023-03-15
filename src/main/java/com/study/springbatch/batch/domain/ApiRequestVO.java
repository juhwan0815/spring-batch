package com.study.springbatch.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestVO {

    private Long id;

    private ProductVO productVO;
}
