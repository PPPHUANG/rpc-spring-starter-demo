package cn.ppphuang.demoservice;

import java.util.List;

public interface DemoService {
    /**
     * @param name String
     * @param age  age
     * @return age
     */
    String hello(String name, Integer age);

    /**
     * @param name String
     * @return age
     */
    String hello(String name);

    /**
     * @param age
     * @return
     */
    Integer helloInteger(Integer age);

    /**
     * @param age int
     * @return int
     */
    int helloInt(int age);

    /**
     * @param age age
     * @return byte
     */
    byte helloByte(byte age);

    /**
     * @param age Boolean
     * @return boolean
     */
    boolean helloBoolean(Boolean age);

    /**
     * @param name String
     * @param age  Integer
     * @return Result<Person>
     */
    Result<List<Person>> helloPerson(String name, Integer age);

    /**
     * @param age Integer
     * @return List<Integer>
     */
    List<Integer> helloList(Integer age);
}
