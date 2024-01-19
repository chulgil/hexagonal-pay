package me.chulgil.msa.batch.adapter.in.batch.partition.processor;

import me.chulgil.msa.batch.adapter.out.service.ApiDomainRequest;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import org.springframework.batch.item.ItemProcessor;

public class ApiItemProcessor implements ItemProcessor<ProductInfo, ApiDomainRequest> {

    @Override
    public ApiDomainRequest process(ProductInfo item) throws Exception {
        return ApiDomainRequest.builder()
                .id(item.getId())
                .productInfo(item)
                .build();
    }

}
