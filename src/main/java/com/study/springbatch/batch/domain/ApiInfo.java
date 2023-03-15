package com.study.springbatch.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfo {

    private String url;
    private List<? extends ApiRequestVO> apiRequestList;

}
