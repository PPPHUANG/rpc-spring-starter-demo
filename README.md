# rpc-spring-starter-demo

### demoservice

服务接口

### demoserver

实现服务接口

### democlient

服务调用

### 本地运行

1. 克隆`rpc-spring-starter`到本地install
   ```shell
   git clone git@github.com:PPPHUANG/rpc-spring-starter.git
   mvn clean install -DskipTests=true
   ```
2. 启动ZK（默认地址是127.0.0.1:2128，其他ZK地址可在项目配置文件中修改）
3. 启动`demoserver`
4. 启动`democlient`
5. 调用`democlient`服务中的HTTP接口即可
   ```
   http://localhost:8090/test
   http://localhost:8090/test2
   http://localhost:8090/test3
   ```
6. 手动获取代理对象

```java
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

```