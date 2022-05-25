package cn.ppphuang.democlient.service;

import cn.ppphuang.democlient.asyncutil.WebClientFutureFactory;
import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import cn.ppphuang.rpcspringstarter.annotation.InjectService;
import cn.ppphuang.rpcspringstarter.client.async.AsyncExecutor;
import cn.ppphuang.rpcspringstarter.client.async.DefaultValueHandle;
import cn.ppphuang.rpcspringstarter.client.async.TimeoutUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class TestService {
    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    @InjectService
    DemoService demoService;
    @InjectService(group = "group2", version = "version2")
    DemoService demoService2;
    @InjectService(group = "group3", version = "version3")
    DemoService demoService3;

    @Autowired()
    AsyncExecutor<DemoService> demoServiceAsyncExecutor;

    WebClient localhostWebClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    public Result<List<Person>> hello(String name) {
        return demoService.helloPerson(name, 1);
    }

    public Result<List<Person>> hello2(String name) {
        return demoService2.helloPerson(name, 2);
    }

    public Result<List<Person>> hello3(String name) {
        return demoService3.helloPerson(name, 3);
    }

    public UserModel async() {
        //这个一秒执行时间
        CompletableFuture<String> ppp1 = demoServiceAsyncExecutor.async(service -> service.hello("ppp"));
        //这个两秒执行时间
        CompletableFuture<String> ppp2 = demoServiceAsyncExecutor.async(service -> service.hello("ppp", 18));
        CompletableFuture<String> timeoutPpp = TimeoutUtils.timeout(ppp2, 3, TimeUnit.SECONDS);
        CompletableFuture<UserModel> userModelCompletableFuture = CompletableFuture.completedFuture(new UserModel())
                .thenCombine(ppp1, ((userModel, s) -> {
                    userModel.setName(s);
                    return userModel;
                })).thenCombine(timeoutPpp, ((userModel, s) -> {
                    userModel.setAge(19);
                    return userModel;
                })).exceptionally(e -> new UserModel());
        //总体2秒执行时间
        return userModelCompletableFuture.join();
    }

    public UserModel async1() {
        CompletableFuture<String> ppp1 = demoServiceAsyncExecutor.async(service -> service.getName("ppp")).handle(new DefaultValueHandle<>(true, "pppp", "service.getName", "ppp"));
        CompletableFuture<Integer> ppp2 = demoServiceAsyncExecutor.async(service -> service.getAge(18));
        CompletableFuture<Integer> timeoutPpp = TimeoutUtils.timeout(ppp2, 3, TimeUnit.SECONDS).handle(new DefaultValueHandle<>(true, 20, "service.getAge", 18));
        CompletableFuture<UserModel> userModelCompletableFuture = CompletableFuture.completedFuture(new UserModel()).thenCombine(ppp1, ((userModel, s) -> {
            userModel.setName(s);
            return userModel;
        })).thenCombine(timeoutPpp, ((userModel, s) -> {
            userModel.setAge(s);
            return userModel;
        })).exceptionally(e -> new UserModel());
        //总体2秒执行时间
        return userModelCompletableFuture.join();
    }

    public UserModel async2() {
        CompletableFuture<String> ppp1 = demoServiceAsyncExecutor.async(service -> service.getName("ppp")).handle(new DefaultValueHandle<>(true, "pppp", "service.getName", "ppp"));
        CompletableFuture<Integer> ppp2 = demoServiceAsyncExecutor.async(service -> service.getAge(18));
        //超时处理
        CompletableFuture<Integer> timeoutPpp = TimeoutUtils.timeout(ppp2, 1, TimeUnit.SECONDS).handle(new DefaultValueHandle<>(true, 20, "service.getAge", 18));

        CompletableFuture<String> asyncHttp = asyncHttp("/hello");
        //总体2秒执行时间
        UserModel userModel1 = CompletableFuture.allOf(ppp1, timeoutPpp, asyncHttp).thenApply(r -> {
            String name = ppp1.join();
            Integer age = timeoutPpp.join();
            String hello = asyncHttp.join();
            UserModel userModel = new UserModel();
            userModel.setName(name);
            userModel.setAge(age);
            userModel.setHello(hello);
            return userModel;
        }).join();
        return userModel1;
    }

    public CompletableFuture<String> asyncHttp(String url) {
        Mono<HttpResult<String>> userMono = localhostWebClient.method(HttpMethod.GET).uri(url)
                .retrieve()
//                .bodyToMono(HttpResult.class)
                .bodyToMono(new ParameterizedTypeReference<HttpResult<String>>() {
                })
                //异常处理
                .onErrorReturn(new HttpResult<>(201, "default", "default hello"))
                //超时处理
                .timeout(Duration.ofSeconds(3))
                //错误记录
                .doOnError(e -> {
                    log.error("doOnError: {}", e.getMessage());
                })
                //返回值过滤
                .filter(httpResult -> httpResult.code == 200)
                //默认值
                .defaultIfEmpty(new HttpResult<String>())
                //失败重试
                .retryWhen(Retry.backoff(1, Duration.ofSeconds(1)));
        CompletableFuture<HttpResult<String>> stringCompletableFuture = WebClientFutureFactory.getCompletableFuture(userMono);
        return stringCompletableFuture.thenApply(HttpResult::getData);
    }

}
