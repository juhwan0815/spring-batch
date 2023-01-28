package com.study.springbatch.batch.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiRequestVO {

    private Long id;

    private ProductVO productVO;

    private ApiResponseV0 apiResponseV0;

}