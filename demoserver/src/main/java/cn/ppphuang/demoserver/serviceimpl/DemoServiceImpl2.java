package cn.ppphuang.demoserver.serviceimpl;

import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import cn.ppphuang.rpcspringstarter.annotation.RpcService;

import java.util.Arrays;
import java.util.List;

@RpcService(group = "group2", version = "version2")
public class DemoServiceImpl2 implements DemoService {
    @Override
    public String hello(String name, Integer age) {
        return name + age + 2;
    }

    @Override
    public String hello(String name) {
        return "a2";
    }

    @Override
    public Integer helloInteger(Integer age) {
        return 2;
    }

    @Override
    public int helloInt(int age) {
        return 2;
    }

    @Override
    public byte helloByte(byte age) {
        return 2;
    }

    @Override
    public boolean helloBoolean(Boolean age) {
        return age;
    }

    @Override
    public Result<List<Person>> helloPerson(String name, Integer age) {
        return new Result<List<Person>>(200, "success2", Arrays.asList(new Person(2, "ppphuang2"), new Person(2, "ppphuang2")));
    }

    @Override
    public List<Integer> helloList(Integer age) {
        return Arrays.asList(2, 2);
    }

    @Override
    public String getName(String name) {
        return null;
    }

    @Override
    public Integer getAge(Integer age) {
        return null;
    }
}
