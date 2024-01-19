package me.chulgil.msa.batch.adapter.in.batch.chunk.processor;

import me.chulgil.msa.batch.adapter.out.persistence.ProductJpaEntity;
import me.chulgil.msa.batch.scheduler.mapper.ProductMapper;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductInfo, ProductJpaEntity> {

    @Override
    public ProductJpaEntity process(ProductInfo item) throws Exception {
        return ProductMapper.INSTANCE.from(item);
    }
}
