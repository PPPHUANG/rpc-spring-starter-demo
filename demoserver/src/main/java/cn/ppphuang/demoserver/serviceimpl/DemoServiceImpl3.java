package cn.ppphuang.demoserver.serviceimpl;

import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import cn.ppphuang.rpcspringstarter.annotation.RpcService;

import java.util.Arrays;
import java.util.List;

@RpcService(value = "cn.ppphuang.demoservice.DemoService", group = "group3", version = "version3")
public class DemoServiceImpl3 implements DemoService, PersionService {
    @Override
    public String hello(String name, Integer age) {
        return name + age + 3;
    }

    @Override
    public String hello(String name) {
        return talk() + "a3";
    }

    @Override
    public Integer helloInteger(Integer age) {
        return 3;
    }

    @Override
    public int helloInt(int age) {
        return 3;
    }

    @Override
    public byte helloByte(byte age) {
        return 3;
    }

    @Override
    public boolean helloBoolean(Boolean age) {
        return age;
    }

    @Override
    public Result<List<Person>> helloPerson(String name, Integer age) {
        return new Result<List<Person>>(200, "success3", Arrays.asList(new Person(3, "ppphuang3"), new Person(3, "ppphuang3")));
    }

    @Override
    public List<Integer> helloList(Integer age) {
        return Arrays.asList(3, 3);
    }

    @Override
    public String talk() {
        return "talk";
    }
}
