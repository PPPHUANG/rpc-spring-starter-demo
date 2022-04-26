package cn.ppphuang.democlient.asyncutil;

import cn.ppphuang.rpcspringstarter.client.async.AsyncCallBackExecutor;
import cn.ppphuang.rpcspringstarter.client.async.AsyncReceiveHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class AsyncCallBack extends AsyncReceiveHandler {
    private static final Logger log = LoggerFactory.getLogger(AsyncCallBackExecutor.class);
    private static volatile AsyncCallBack INSTANCE;

    private AsyncCallBack() {
    }

    public static AsyncCallBack instance() {
        if (INSTANCE == null) {
            synchronized (AsyncCallBack.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AsyncCallBack();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void callBack(Object context, Object result) {
        if (!(context instanceof CompletableFuture)) {
            throw new IllegalStateException("the context must be CompletableFuture");
        }
        CompletableFuture future = (CompletableFuture) context;
        if (result instanceof Throwable) {
            future.completeExceptionally((Throwable) result);
            return;
        }
        log.info("result:{}", result);
        future.complete(result);
    }

    @Override
    public void onException(Object context, Object result, Exception e) {

    }
}
