package com.study.springbatch.batch.classifier;

import com.study.springbatch.batch.domain.ApiRequestVO;
import com.study.springbatch.batch.domain.ProductVO;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import java.util.Map;

@Setter
public class WriterClassifier<C, T> implements Classifier<C, T> {

    private Map<String, ItemWriter<ApiRequestVO>> writerMap;

    @Override
    public T classify(C classifiable) {
        return (T) writerMap.get(((ApiRequestVO)classifiable).getProductVO().getType());
    }
}
