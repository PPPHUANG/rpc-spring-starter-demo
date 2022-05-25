package cn.ppphuang.democlient.asyncutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class WebClientFutureFactory {
    private static final Logger log = LoggerFactory.getLogger(WebClientFutureFactory.class);

    public static <T> CompletableFuture<T> getCompletableFuture(Mono<T> mono) {
        CompletableFuture<T> stringCompletableFuture = new CompletableFuture<>();
        mono.subscribe(result -> {
            stringCompletableFuture.complete(result);
            log.info("mono.subscribe execute thread: {}", Thread.currentThread().getName());
        });
        return stringCompletableFuture;
    }
}
