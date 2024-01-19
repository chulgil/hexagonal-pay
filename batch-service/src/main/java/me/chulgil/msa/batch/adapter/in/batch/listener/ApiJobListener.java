package me.chulgil.msa.batch.adapter.in.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class ApiJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {}

    @Override
    public void afterJob(JobExecution jobExecution) {
        long executionTime = calculateExecutionTime(jobExecution);
        System.out.println("Execution time: " + executionTime + "ms");
    }

    private long calculateExecutionTime(JobExecution jobExecution) {
        return jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();
    }
}
