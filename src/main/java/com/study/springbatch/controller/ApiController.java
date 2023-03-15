package com.study.springbatch.controller;

import com.study.springbatch.batch.domain.ApiInfo;
import com.study.springbatch.batch.domain.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ApiController {

    @PostMapping("/api/product/1")
    public String product1(@RequestBody ApiInfo apiInfo) {
        List<ProductVO> products = apiInfo.getApiRequestList().stream()
                .map(item -> item.getProductVO())
                .collect(Collectors.toList());

        log.info("products = {}", products);
        return "product1 was success";
    }

    @PostMapping("/api/product/2")
    public String product2(@RequestBody ApiInfo apiInfo) {
        List<ProductVO> products = apiInfo.getApiRequestList().stream()
                .map(item -> item.getProductVO())
                .collect(Collectors.toList());

        log.info("products = {}", products);
        return "product2 was success";
    }

    @PostMapping("/api/product/3")
    public String product3(@RequestBody ApiInfo apiInfo) {
        List<ProductVO> products = apiInfo.getApiRequestList().stream()
                .map(item -> item.getProductVO())
                .collect(Collectors.toList());

        log.info("products = {}", products);
        return "product3 was success";
    }

}
