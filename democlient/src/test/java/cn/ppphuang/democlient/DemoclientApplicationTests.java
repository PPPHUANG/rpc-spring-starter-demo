package cn.ppphuang.democlient;

import cn.ppphuang.democlient.service.TestUnitCallBackHandler;
import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.rpcspringstarter.client.net.ClientProxyFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoclientApplicationTests {

    @Autowired
    ClientProxyFactory clientProxyFactory;

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
}
