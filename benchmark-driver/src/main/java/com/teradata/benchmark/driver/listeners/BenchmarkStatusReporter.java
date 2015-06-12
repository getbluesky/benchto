package com.teradata.benchmark.driver.listeners;

import com.teradata.benchmark.driver.Benchmark;
import com.teradata.benchmark.driver.BenchmarkResult;
import com.teradata.benchmark.driver.sql.QueryExecution;
import com.teradata.benchmark.driver.sql.QueryExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BenchmarkStatusReporter
{

    @Autowired
    private List<BenchmarkExecutionListener> executionListeners;

    @Qualifier("defaultTaskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    public void reportBenchmarkStarted(Benchmark benchmark)
    {
        for (BenchmarkExecutionListener listener : executionListeners) {
            taskExecutor.execute(() -> listener.benchmarkStarted(benchmark));
        }
    }

    public void reportBenchmarkFinished(BenchmarkResult result)
    {
        for (BenchmarkExecutionListener listener : executionListeners) {
            taskExecutor.execute(() -> listener.benchmarkFinished(result));
        }
    }

    public void reportExecutionStarted(QueryExecution queryExecution)
    {
        for (BenchmarkExecutionListener listener : executionListeners) {
            taskExecutor.execute(() -> listener.executionStarted(queryExecution));
        }
    }

    public void reportExecutionFinished(QueryExecutionResult execution)
    {
        for (BenchmarkExecutionListener listener : executionListeners) {
            taskExecutor.execute(() -> listener.executionFinished(execution));
        }
    }

    public void reportBenchmarkFinished(List<BenchmarkResult> benchmarkResults)
    {
        for (BenchmarkExecutionListener listener : executionListeners) {
            taskExecutor.execute(() -> listener.suiteFinished(benchmarkResults));
        }
    }
}
