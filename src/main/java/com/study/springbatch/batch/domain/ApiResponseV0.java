package com.study.springbatch.batch.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseV0 {

    private int status;

    private String msg;

}
