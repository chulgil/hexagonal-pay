package me.chulgil.msa.batch.adapter.in.scheduler;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiStepBatch {

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private int chunkSize = 10;

    @Bean
    public Step apiMasterStep() {
        return stepBuilderFactory.get("apiMasterStep")
                                 .partitioner(apiSlaveStep().getName(), partitioner())
                                 .step(apiSlaveStep())
                                 .gridSize(3)
                                 .taskExecutor(taskExecutor())
                                 .build();
    }

    @Bean
    public Step apiSlaveStep() {
        return stepBuilderFactory.get("apiSlaveStep")
                                 .<Pro, String>chunk(chunkSize)
                                 .reader(reader())
                                 .writer(writer())
                                 .build();
    }
}
