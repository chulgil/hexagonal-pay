package me.chulgil.msa.batch.scheduler.job.api;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.batch.adapter.in.batch.partition.ProductPartitioner;
import me.chulgil.msa.batch.adapter.in.batch.partition.classifier.ProcessorClassifier;
import me.chulgil.msa.batch.adapter.in.batch.partition.classifier.WriterClassifier;
import me.chulgil.msa.batch.adapter.in.batch.partition.processor.ApiItemProcessor;
import me.chulgil.msa.batch.adapter.in.batch.partition.writer.ApiItemWriter;
import me.chulgil.msa.batch.adapter.out.service.ApiDomainRequest;
import me.chulgil.msa.batch.scheduler.port.out.FindProductPort;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import me.chulgil.msa.batch.scheduler.port.out.QueryParameterPort;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class ApiStepConfiguration {

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final FindProductPort findProductPort;
    private final QueryParameterPort queryParameterPort;
    private int chunkSize = 10;

    @Bean
    public Step apiMasterStep() throws Exception {
        return stepBuilderFactory.get("apiMasterStep")
                                 .partitioner(apiSlaveStep().getName(), partitioner())
                                 .step(apiSlaveStep())
                                 .gridSize(3)
                                 .taskExecutor(taskExecutor())
                                 .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(6);
        taskExecutor.setThreadNamePrefix("batch-api-thread");
        return taskExecutor;
    }

    @Bean
    public Step apiSlaveStep() throws Exception {
        return stepBuilderFactory.get("apiSlaveStep")
                                 .<ProductInfo, ProductInfo>chunk(chunkSize)
                                 .reader(apiItemReader(null))
                                 .processor(apiItemProcessor())
                                 .writer(apiItemWriter())
                                 .build();
    }

    @Bean
    public ProductPartitioner partitioner() {
        return ProductPartitioner.builder()
                .dataSource(dataSource)
                .findProductPort(findProductPort)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<ProductInfo> apiItemReader(@Value("#{stepExecutionContext['product']}") ProductInfo product)
            throws Exception {

        JdbcPagingItemReader<ProductInfo> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(dataSource);
        reader.setPageSize(chunkSize);
        reader.setRowMapper(new BeanPropertyRowMapper<ProductInfo>());

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, name, price, type");
        queryProvider.setFromClause("from product");
        queryProvider.setWhereClause("where type = :type");

        HashMap<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.DESCENDING);
        queryProvider.setSortKeys(sortKeys);

        reader.setParameterValues(queryParameterPort.getParameterForQuery("type", product.getType()));
        reader.setQueryProvider(queryProvider);
        reader.afterPropertiesSet();

        return reader;
    }

    @Bean
    public ItemProcessor apiItemProcessor() {

        ClassifierCompositeItemProcessor<ProductInfo, ApiDomainRequest> processor =
                new ClassifierCompositeItemProcessor<>();
        Map<String, ItemProcessor<ProductInfo, ApiDomainRequest>> processorMap = new HashMap<>();
        processorMap.put("1", new ApiItemProcessor());
        ProcessorClassifier<ProductInfo, ItemProcessor<?, ? extends ApiDomainRequest>> classifier =
                new ProcessorClassifier(processorMap);

        processor.setClassifier(classifier);
        return processor;
    }

    private ItemWriter apiItemWriter() {
        ClassifierCompositeItemWriter<ApiDomainRequest> processor = new ClassifierCompositeItemWriter<>();
        Map<String, ItemWriter<ApiDomainRequest>> processorMap = new HashMap<>();
        processorMap.put("1", new ApiItemWriter());
        WriterClassifier<ApiDomainRequest, ItemWriter<? super ApiDomainRequest>> classifier =
                new WriterClassifier(processorMap);

        processor.setClassifier(classifier);
        return processor;
    }

}
