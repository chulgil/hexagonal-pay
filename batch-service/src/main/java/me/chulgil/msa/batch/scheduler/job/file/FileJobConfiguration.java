package me.chulgil.msa.batch.scheduler.job.file;

import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.batch.adapter.in.batch.chunk.processor.FileItemProcessor;
import me.chulgil.msa.batch.adapter.out.persistence.ProductJpaEntity;
import me.chulgil.msa.batch.scheduler.port.out.ProductInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                                .start(fileStep())
                                .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep")
                                 .<ProductInfo, ProductJpaEntity>chunk(10)
                                 .reader(fileItemReader(null))
                                 .processor(fileItemProcessor())
                                 .writer(fileItemWriter())
                                 .build();

    }

    @Bean
    @JobScope
    public FlatFileItemReader<ProductInfo> fileItemReader(@Value("#{jobParameters['requireDate']}") String requireDate) {
        return new FlatFileItemReaderBuilder<ProductInfo>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requireDate + ".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductInfo.class)
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("id", "name", "price", "type")
                .build();
    }

    @Bean
    public ItemProcessor<ProductInfo, ProductJpaEntity> fileItemProcessor() {
        return new FileItemProcessor();
    }

    @Bean
    public ItemWriter<ProductJpaEntity> fileItemWriter() {
        return new JpaItemWriterBuilder<ProductJpaEntity>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}
