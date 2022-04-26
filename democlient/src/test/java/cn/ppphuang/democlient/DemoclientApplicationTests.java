package cn.ppphuang.democlient;

import cn.ppphuang.democlient.asyncutil.AsyncExecutor;
import cn.ppphuang.democlient.service.TestUnitCallBackHandler;
import cn.ppphuang.democlient.service.UserModel;
import cn.ppphuang.democlient.asyncutil.TimeoutUtils;
import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.rpcspringstarter.client.async.AsyncCallBackExecutor;
import cn.ppphuang.rpcspringstarter.client.net.ClientProxyFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DemoclientApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(AsyncCallBackExecutor.class);

    @Autowired
    ClientProxyFactory clientProxyFactory;

    @Autowired()
    AsyncExecutor<DemoService> demoServiceAsyncExecutor;

    @Test
    void contextLoads() {
    }

    @Test
    void test() throws InterruptedException {
        DemoService proxy = clientProxyFactory.getProxy(DemoService.class);
        String ppphuang = proxy.hello("ppphuang");
        System.out.println(ppphuang);
    }

    @Test
    void testSyncGroup2Version2() throws InterruptedException {
        DemoService proxy = clientProxyFactory.getProxy(DemoService.class, "group2", "version2");
        String ppphuang = proxy.hello("ppphuang");
        System.out.println(ppphuang);
    }

    @Test
    void testSyncGroup3Version3() throws InterruptedException {
        DemoService proxy = clientProxyFactory.getProxy(DemoService.class, "group3", "version3");
        String ppphuang = proxy.hello("ppphuang");
        System.out.println(ppphuang);
    }

    @Test
    void testAsync() throws InterruptedException {
        DemoService proxy = clientProxyFactory.getProxy(DemoService.class, "group2", "version2", true);
        TestUnitCallBackHandler callBackHandler = new TestUnitCallBackHandler();
        ClientProxyFactory.setLocalAsyncContextAndAsyncReceiveHandler("context", callBackHandler);
        String ppphuang = proxy.hello("ppphuang");
        System.out.println(ppphuang);
        Thread.sleep(10000);
    }

    @Test
    void testAsyncCompletableFuture() {
        long t1 = System.currentTimeMillis();
        CompletableFuture<String> ppp1 = demoServiceAsyncExecutor.async(service -> service.hello("ppp"));
        CompletableFuture<String> ppp2 = demoServiceAsyncExecutor.async(service -> service.hello("ppp", 18));
        CompletableFuture<String> timeoutPpp2 = TimeoutUtils.timeout(ppp2, 1, TimeUnit.SECONDS);
        CompletableFuture<String> stringFuture = ppp1.thenCombine(timeoutPpp2, (a, b) -> a + b).exceptionally(e -> {
            log.error("error:", e);
            return "error";
        });
        log.info(stringFuture.join());
        long t2 = System.currentTimeMillis();
        log.info("sum:{}", t2 - t1);
    }

    @Test
    void testAsyncCompletableFutureB() {
        CompletableFuture<String> ppp1 = demoServiceAsyncExecutor.async(service -> service.hello("ppp"));
        CompletableFuture<String> ppp2 = demoServiceAsyncExecutor.async(service -> service.hello("ppp", 18));
        CompletableFuture<UserModel> userModelCompletableFuture = CompletableFuture.completedFuture(new UserModel())
                .thenCombine(ppp1, ((userModel, s) -> {
                    userModel.setName(s);
                    return userModel;
                })).thenCombine(ppp2, ((userModel, s) -> {
                    userModel.setAge(19);
                    return userModel;
                }));
        System.out.println(userModelCompletableFuture.join());
    }
}
