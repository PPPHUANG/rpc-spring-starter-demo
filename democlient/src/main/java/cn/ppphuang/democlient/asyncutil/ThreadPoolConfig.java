package cn.ppphuang.democlient.asyncutil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
    /**
     * 参数初始化
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数量大小
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    /**
     * 线程池最大容纳线程数
     */
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 阻塞队列
     */
    private static final int WORK_QUEUE = 20;
    /**
     * 线程空闲后的存活时长
     */
    private static final int KEEP_ALIVE_TIME = 30;

    @Bean("asyncTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        //等待队列
        threadPoolTaskExecutor.setQueueCapacity(WORK_QUEUE);
        //线程前缀
        threadPoolTaskExecutor.setThreadNamePrefix("asyncTaskExecutor-");
        //线程池维护线程所允许的空闲时间,单位为秒
        threadPoolTaskExecutor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        // 线程池对拒绝任务(无线程可用)的处理策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
