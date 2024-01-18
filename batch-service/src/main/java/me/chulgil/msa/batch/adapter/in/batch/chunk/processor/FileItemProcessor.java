package me.chulgil.msa.batch.adapter.in.batch.chunk.processor;

import me.chulgil.msa.batch.adapter.out.persistence.ProductJpaEntity;
import me.chulgil.msa.batch.scheduler.mapper.ProductMapper;
import me.chulgil.msa.batch.scheduler.port.out.ProductVO;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, ProductJpaEntity> {

    @Override
    public ProductJpaEntity process(ProductVO item) throws Exception {
        return ProductMapper.INSTANCE.from(item);
    }
}
