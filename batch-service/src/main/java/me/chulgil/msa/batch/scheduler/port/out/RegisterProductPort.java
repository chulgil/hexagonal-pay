package me.chulgil.msa.batch.scheduler.port.out;

import me.chulgil.msa.batch.adapter.out.persistence.ProductJpaEntity;

public interface RegisterProductPort {
    ProductJpaEntity registerProduct(String productName, int price, String productType);

}
