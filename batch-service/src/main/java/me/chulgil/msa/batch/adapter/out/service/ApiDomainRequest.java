package me.chulgil.msa.batch.adapter.out.service;

import lombok.Builder;
import lombok.Data;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;

@Data
@Builder
public class ApiDomainRequest {

    private Long id;
    private ProductInfo productInfo;
}
