package me.chulgil.msa.batch.adapter.in.batch.partition.classifier;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.batch.adapter.out.service.ApiDomainRequest;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

@RequiredArgsConstructor
public class WriterClassifier<C, T> implements Classifier<C, T> {

    private final Map<String, ItemProcessor<ProductInfo, ApiDomainRequest>> typeToProcessorMap;

    @Override
    public T classify(C classifiable) {
        final ProductInfo productInfo = (ProductInfo) classifiable;
        return (T) typeToProcessorMap.get(productInfo.getType());
    }

}
