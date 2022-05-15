package cn.ppphuang.demoserver.serviceimpl;

import cn.ppphuang.demoservice.DemoService;
import cn.ppphuang.demoservice.Person;
import cn.ppphuang.demoservice.Result;
import cn.ppphuang.rpcspringstarter.annotation.RpcService;
import cn.ppphuang.rpcspringstarter.client.async.AsyncCallBackExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@RpcService
public class DemoServiceImpl implements DemoService {

    private static final Logger log = LoggerFactory.getLogger(AsyncCallBackExecutor.class);

    @Override
    public String hello(String name, Integer age) {
        log.info("hello2 start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("hello2 end");
        return name + age;
    }

    @Override
    public String hello(String name) {
        log.info("hello1 start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("hello1 end");
        return name;
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

    @Override
    public String getName(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "name is " + name;
    }

    @Override
    public Integer getAge(Integer age) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return age + 1;
    }
}
