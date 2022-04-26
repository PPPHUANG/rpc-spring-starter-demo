package cn.ppphuang.democlient.asyncutil;

import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.rpcspringstarter.client.net.ClientProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AsyncExecutorConfig {
    @Autowired
    ClientProxyFactory clientProxyFactory;

    @Bean
    public AsyncExecutor<DemoService> demoServiceAsyncExecutor() {
        return new AsyncExecutor<>(clientProxyFactory, DemoService.class, "", "");
    }
}
