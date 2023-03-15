package com.study.springbatch.batch.classifier;

import com.study.springbatch.batch.domain.ApiRequestVO;
import com.study.springbatch.batch.domain.ProductVO;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.Map;

@Setter
public class ProcessorClassifier<C, T> implements Classifier<C, T> {

    private Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap;

    @Override
    public T classify(C classifiable) {
        return (T) processorMap.get(((ProductVO)classifiable).getType());
    }
}
