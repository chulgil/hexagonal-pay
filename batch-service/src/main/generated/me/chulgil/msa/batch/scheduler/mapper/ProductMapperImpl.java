package me.chulgil.msa.batch.scheduler.mapper;

import javax.annotation.processing.Generated;
import me.chulgil.msa.batch.adapter.out.persistence.ProductJpaEntity;
import me.chulgil.msa.batch.scheduler.port.out.ProductVO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-16T17:46:56+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.21 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductVO to(ProductJpaEntity arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ProductVO.ProductVOBuilder productVO = ProductVO.builder();

        productVO.id( arg0.getId() );
        productVO.name( arg0.getName() );
        productVO.price( arg0.getPrice() );
        productVO.type( arg0.getType() );

        return productVO.build();
    }

    @Override
    public ProductJpaEntity from(ProductVO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ProductJpaEntity.ProductJpaEntityBuilder productJpaEntity = ProductJpaEntity.builder();

        productJpaEntity.id( arg0.getId() );
        productJpaEntity.name( arg0.getName() );
        productJpaEntity.price( arg0.getPrice() );
        productJpaEntity.type( arg0.getType() );

        return productJpaEntity.build();
    }
}
