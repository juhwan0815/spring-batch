package com.study.springbatch.batch.chunk.writer;

import com.study.springbatch.batch.domain.ApiRequestVO;
import com.study.springbatch.batch.domain.ApiResponseVO;
import com.study.springbatch.service.ApiService1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ApiItemWriter1 extends FlatFileItemWriter<ApiRequestVO> {

private final ApiService1 apiService1;

@Override
public void write(List<? extends ApiRequestVO> items) throws Exception {
        ApiResponseVO response = apiService1.service(items);
        log.info("response = {}", response);
        items.forEach(apiRequestVO -> {
        apiRequestVO.setProductVO(apiRequestVO.getProductVO());
        });

        super.setResource(new FileSystemResource("/Users/juhwan/study/spring-batch/src/main/resources/product1.txt"));
        super.open(new ExecutionContext());
        super.setLineAggregator(new DelimitedLineAggregator<>());
        super.setAppendAllowed(true);
        super.write(items);
        }

}
