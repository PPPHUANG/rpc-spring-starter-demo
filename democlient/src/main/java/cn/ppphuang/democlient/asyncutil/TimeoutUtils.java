package cn.ppphuang.democlient.asyncutil;

import java.util.concurrent.*;
import java.util.function.Function;

public class TimeoutUtils {
    private static final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(10);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(scheduledExecutor::shutdownNow));
    }

    public static <T> CompletableFuture<T> timeout(CompletableFuture<T> cf, long timeout, TimeUnit unit) {
        CompletableFuture<T> result = new CompletableFuture<>();
        scheduledExecutor.schedule(() -> result.completeExceptionally(new TimeoutException()), timeout, unit);
        return cf.applyToEither(result, Function.identity());
    }
}
