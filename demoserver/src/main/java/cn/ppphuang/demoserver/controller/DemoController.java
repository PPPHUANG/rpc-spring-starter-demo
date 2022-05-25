package cn.ppphuang.demoserver.controller;

import cn.ppphuang.demoservice.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {

    @GetMapping(value = "hello")
    public Result<String> test1(String value) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Result<String>(200, "", "helloHttp");
    }
}
