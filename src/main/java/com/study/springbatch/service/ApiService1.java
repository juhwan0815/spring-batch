package com.study.springbatch.service;

import com.study.springbatch.batch.domain.ApiInfo;
import com.study.springbatch.batch.domain.ApiResponseV0;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService1 extends AbstractApiService {
    @Override
    protected ApiResponseV0 doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:8081/api/product/1", apiInfo, String.class);

        int statusCodeValue = responseEntity.getStatusCodeValue();

        return ApiResponseV0.builder()
                .status(statusCodeValue)
                .msg(responseEntity.getBody())
                .build();
    }
}
