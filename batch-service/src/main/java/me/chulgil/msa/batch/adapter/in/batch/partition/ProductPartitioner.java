package me.chulgil.msa.batch.adapter.in.batch.partition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.batch.scheduler.port.out.FindProductPort;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

@RequiredArgsConstructor
@Builder
public class ProductPartitioner implements Partitioner {

    private final DataSource dataSource;
    private final FindProductPort findProductPort;


    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        List<ProductInfo> products = findProductPort.findProductByType();
        Map<String, ExecutionContext> partitions = new HashMap<>();

        int partitionNumber = 1;
        for (ProductInfo product : products) {
            addPartition(partitions, product, partitionNumber++);
        }

        return partitions;
    }

    private void addPartition(Map<String, ExecutionContext> partitions, ProductInfo product, int partitionNumber) {
        ExecutionContext context = new ExecutionContext();
        context.put("product", product);
        partitions.put("partition" + partitionNumber, context);
    }

}

