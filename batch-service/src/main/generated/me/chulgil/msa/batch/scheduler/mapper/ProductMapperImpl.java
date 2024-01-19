package me.chulgil.msa.batch.scheduler.mapper;

import javax.annotation.processing.Generated;
import me.chulgil.msa.batch.adapter.out.persistence.ProductJpaEntity;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-19T15:03:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.21 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductInfo to(ProductJpaEntity arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ProductInfo.ProductInfoBuilder productInfo = ProductInfo.builder();

        productInfo.id( arg0.getId() );
        productInfo.name( arg0.getName() );
        productInfo.price( arg0.getPrice() );
        productInfo.type( arg0.getType() );

        return productInfo.build();
    }

    @Override
    public ProductJpaEntity from(ProductInfo arg0) {
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
