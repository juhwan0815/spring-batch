package com.study.springbatch;

import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.Map;

@Setter

public class ProcessorClassifier<C, T> implements Classifier<C, T> {

    private Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> processorMap;

    @Override
    public T classify(C classifiable) {
        return (T) processorMap.get(((ProcessorInfo) classifiable).getId());
    }

}
