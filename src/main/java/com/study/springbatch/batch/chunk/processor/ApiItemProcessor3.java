package com.study.springbatch.batch.chunk.processor;

import com.study.springbatch.batch.domain.ApiRequestVO;
import com.study.springbatch.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;


public class ApiItemProcessor3 implements ItemProcessor<ProductVO, ApiRequestVO> {

    @Override
    public ApiRequestVO process(ProductVO item) throws Exception {
        return new ApiRequestVO(item.getId(), item);
    }
}
