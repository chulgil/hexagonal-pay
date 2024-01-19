package me.chulgil.msa.batch.adapter.in.batch.partition.writer;

import java.util.List;
import me.chulgil.msa.batch.adapter.out.service.ApiDomainRequest;
import org.springframework.batch.item.ItemWriter;

public class ApiItemWriter implements ItemWriter<ApiDomainRequest> {

    @Override
    public void write(List<? extends ApiDomainRequest> items) throws Exception {
        for (ApiDomainRequest item : items) {
            System.out.println("item = " + item);
        }
    }

}
