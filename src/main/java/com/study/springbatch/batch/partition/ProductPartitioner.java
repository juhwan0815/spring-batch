package com.study.springbatch.batch.partition;

import com.study.springbatch.batch.domain.ProductVO;
import com.study.springbatch.batch.job.api.QueryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Setter
public class ProductPartitioner implements Partitioner {

    private DataSource dataSource;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        ProductVO[] productList = QueryGenerator.getProductList(dataSource);

        Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;

        for( int i =0; i < productList.length; i++) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);
            value.put("product", productList[i]);
            number++;
        }
        return result;
    }
}
