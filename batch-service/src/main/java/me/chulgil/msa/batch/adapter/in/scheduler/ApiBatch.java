package me.chulgil.msa.batch.adapter.in.scheduler;

import lombok.RequiredArgsConstructor;
import me.chulgil.msa.batch.adapter.in.scheduler.listener.ApiJobListener;
import me.chulgil.msa.batch.adapter.in.scheduler.tasklet.ApiEndTasklet;
import me.chulgil.msa.batch.adapter.in.scheduler.tasklet.ApiStartTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class ApiBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Step jobStep;
    private final ApiStartTasklet apiStartTasklet;
    private final ApiEndTasklet apiEndTasklet;

    @Bean
    public Job apiJob() {
        return jobBuilderFactory.get("apiJob")
                                .listener(new ApiJobListener())
                                .start(apiStep1())
                                .next(jobStep)
                                .next(apiStep2())
                                .build();
    }

    @Bean
    public Step apiStep1() {
        return stepBuilderFactory.get("apiStep1")
                                 .tasklet(apiStartTasklet)
                                 .build();

    }

    @Bean
    public Step apiStep2() {
        return stepBuilderFactory.get("apiStep2")
                                 .tasklet(apiStartTasklet)
                                 .build();
    }

}
