package cn.ppphuang.demoserver.serviceimpl;

import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import cn.ppphuang.rpcspringstarter.annotation.RpcService;

import java.util.Arrays;
import java.util.List;

@RpcService
public class DemoServiceImpl implements DemoService {
    @Override
    public String hello(String name, Integer age) {
        return name + age;
    }

    @Override
    public String hello(String name) {
        return "a1";
    }

    @Override
    public Integer helloInteger(Integer age) {
        return age;
    }

    @Override
    public int helloInt(int age) {
        return age;
    }

    @Override
    public byte helloByte(byte age) {
        return age;
    }

    @Override
    public boolean helloBoolean(Boolean age) {
        return age;
    }

    @Override
    public Result<List<Person>> helloPerson(String name, Integer age) {
        return new Result<List<Person>>(200, "success1", Arrays.asList(new Person(1, "ppphuang1"), new Person(1, "ppphuang1")));
    }

    @Override
    public List<Integer> helloList(Integer age) {
        return Arrays.asList(1, 2);
    }
}
