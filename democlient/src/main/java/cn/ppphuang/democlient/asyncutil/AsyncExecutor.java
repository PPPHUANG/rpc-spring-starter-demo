package cn.ppphuang.democlient.asyncutil;

import cn.ppphuang.rpcspringstarter.client.net.ClientProxyFactory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class AsyncExecutor<C> {

    private C client;

    public AsyncExecutor(ClientProxyFactory clientProxyFactory, Class<C> clazz, String group, String version) {
        this.client = clientProxyFactory.getProxy(clazz, group, version, true);
    }

    public <R> CompletableFuture<R> async(Function<C, R> function) {
        CompletableFuture<R> future = new CompletableFuture<>();
        ClientProxyFactory.setLocalAsyncContextAndAsyncReceiveHandler(future, AsyncCallBack.instance());
        try {
            function.apply(client);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        return future;
    }
}
