package com.study.springbatch.batch.partition;

import com.study.springbatch.batch.domain.ProductVO;
import com.study.springbatch.batch.job.api.QueryGenerator;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ProductPartitioner implements Partitioner {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        ProductVO[] productList = QueryGenerator.getProductList(dataSource);
        Map<String, ExecutionContext> result = new HashMap<>();

        for(int i = 0; i < productList.length; i++) {
            ExecutionContext value = new ExecutionContext();
            value.put("type", productList[i].getType());
            result.put("partition" + i, value);
        }

        return result;
    }
}
