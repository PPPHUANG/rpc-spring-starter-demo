package cn.ppphuang.democlient.service;

import cn.ppphuang.democlient.asyncutil.AsyncExecutor;
import cn.ppphuang.democlient.asyncutil.DefaultValueHandle;
import cn.ppphuang.democlient.asyncutil.TimeoutUtils;
import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import cn.ppphuang.rpcspringstarter.annotation.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class TestService {
    @InjectService
    DemoService demoService;
    @InjectService(group = "group2", version = "version2")
    DemoService demoService2;
    @InjectService(group = "group3", version = "version3")
    DemoService demoService3;

    @Autowired()
    AsyncExecutor<DemoService> demoServiceAsyncExecutor;

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
        CompletableFuture<String> ppp1 = demoServiceAsyncExecutor.async(service -> service.getName("ppp"))
                .handle(new DefaultValueHandle<>(true, "pppp", "service.getName", "ppp"));
        CompletableFuture<Integer> ppp2 = demoServiceAsyncExecutor.async(service -> service.getAge(18));
        CompletableFuture<Integer> timeoutPpp = TimeoutUtils.timeout(ppp2, 3, TimeUnit.SECONDS)
                .handle(new DefaultValueHandle<>(true, 20, "service.getAge", 18));
        CompletableFuture<UserModel> userModelCompletableFuture = CompletableFuture.completedFuture(new UserModel())
                .thenCombine(ppp1, ((userModel, s) -> {
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
        CompletableFuture<String> ppp1 = demoServiceAsyncExecutor.async(service -> service.getName("ppp"))
                .handle(new DefaultValueHandle<>(true, "pppp", "service.getName", "ppp"));
        CompletableFuture<Integer> ppp2 = demoServiceAsyncExecutor.async(service -> service.getAge(18));
        //超时处理
        CompletableFuture<Integer> timeoutPpp = TimeoutUtils.timeout(ppp2, 1, TimeUnit.SECONDS)
                .handle(new DefaultValueHandle<>(true, 20, "service.getAge", 18));
        //总体2秒执行时间
        UserModel userModel1 = CompletableFuture.allOf(ppp1, timeoutPpp).thenApply(r -> {
            String name = ppp1.join();
            Integer age = timeoutPpp.join();
            UserModel userModel = new UserModel();
            userModel.setName(name);
            userModel.setAge(age);
            return userModel;
        }).join();
        return userModel1;
    }

}
