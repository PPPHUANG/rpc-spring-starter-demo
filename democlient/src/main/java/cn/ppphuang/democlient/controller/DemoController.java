package cn.ppphuang.democlient.controller;

import cn.ppphuang.democlient.service.TestService;
import cn.ppphuang.democlient.service.UserModel;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    @Autowired
    TestService service;

    @GetMapping(value = "test")
    public Result<List<Person>> test1(String pass) {
        return service.hello("ppphuang");
    }

    @GetMapping(value = "test2")
    public Result<List<Person>> test2(String pass) {
        return service.hello2("ppphuang");
    }

    @GetMapping(value = "test3")
    public Result<List<Person>> test3(String pass) {
        return service.hello3("ppphuang");
    }

    @GetMapping(value = "async")
    public UserModel async() {
        return service.async();
    }
}
