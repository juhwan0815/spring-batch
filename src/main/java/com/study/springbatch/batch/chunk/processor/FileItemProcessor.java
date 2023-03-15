package com.study.springbatch.batch.chunk.processor;

import com.study.springbatch.batch.domain.Product;
import com.study.springbatch.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {
    @Override
    public Product process(ProductVO item) throws Exception {
        Product product = new Product(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getType()
        );
        return product;
    }
}
