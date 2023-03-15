package com.study.springbatch.service;

import com.study.springbatch.batch.domain.ApiInfo;
import com.study.springbatch.batch.domain.ApiResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiService3 extends AbstractApiService{
    @Override
    public ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/api/product/3", apiInfo, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        return new ApiResponseVO(statusCodeValue, responseEntity.getBody());
    }
}
