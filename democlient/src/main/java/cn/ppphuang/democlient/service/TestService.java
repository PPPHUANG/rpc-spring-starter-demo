package cn.ppphuang.democlient.service;

import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import cn.ppphuang.rpcspringstarter.annotation.InjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @InjectService
    DemoService demoService;
    @InjectService(group = "group2", version = "version2")
    DemoService demoService2;
    @InjectService(group = "group3", version = "version3")
    DemoService demoService3;

    public Result<List<Person>> hello(String name) {
        return demoService.helloPerson(name, 1);
    }

    public Result<List<Person>> hello2(String name) {
        return demoService2.helloPerson(name, 2);
    }

    public Result<List<Person>> hello3(String name) {
        return demoService3.helloPerson(name, 3);
    }
}
