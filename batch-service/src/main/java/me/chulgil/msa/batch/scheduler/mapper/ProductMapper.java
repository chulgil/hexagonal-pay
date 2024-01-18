package me.chulgil.msa.batch.scheduler.mapper;

import me.chulgil.msa.batch.adapter.out.persistence.ProductJpaEntity;
import me.chulgil.msa.batch.scheduler.port.out.ProductVO;
import me.chulgil.msa.common.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<ProductVO, ProductJpaEntity> {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

}
