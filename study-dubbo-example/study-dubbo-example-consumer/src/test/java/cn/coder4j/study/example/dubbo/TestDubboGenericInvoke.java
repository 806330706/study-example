/*
 * *
 *  * blog.coder4j.cn
 *  * Copyright (C) 2016-2019 All Rights Reserved.
 *
 */
package cn.coder4j.study.example.dubbo;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.assertj.core.util.Lists;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * <link>http://dubbo.apache.org/zh-cn/docs/user/demos/generic-reference.html</link>
 * @author buhao
 * @version TestDubboGenericInvoke.java, v 0.1 2019-09-16 19:08 buhao
 */
public class TestDubboGenericInvoke extends BaseTest {

    private static ReferenceConfig<GenericService> reference;

    @BeforeClass
    public static void init() {
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        reference = new ReferenceConfig<>();
    }

    /**
     * 简单类型的泛化调用
     */
    @Test
    public void testSimpleType() {

        // 引用远程服务
        reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface("cn.coder4j.study.example.dubbo.DemoService");
        reference.setVersion("1.0.0");
        // 声明为泛化接口
        reference.setGeneric(true);

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        String param = "world";
        Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{param});

        assertEquals(("Hello " + param), result.toString());
    }

    /**
     * 复杂类型的泛化调用
     */
    @Test
    public void testCustomType() {

        // 引用远程服务
        reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface("cn.coder4j.study.example.dubbo.DemoService");
        reference.setVersion("1.0.0");
        // 声明为泛化接口
        reference.setGeneric(true);

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
        Map<String, Object> person = new HashMap<>();
        person.put("name", "kiwi");
        person.put("age", "18");

        // 如果返回POJO将自动转成Map
        Object result = genericService.$invoke("findGirlFriend", new String[]
                {"cn.coder4j.study.example.dubbo.model.Person"}, new Object[]{person});

        Map<String, Object> resultMap = (Map<String, Object>) result;
        assertEquals("小萍", resultMap.get("name").toString());
    }

    /**
     * 泛型的泛化调用
     */
    @Test
    public void testGenericityType() {

        // 引用远程服务
        reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface("cn.coder4j.study.example.dubbo.DemoService");
        reference.setVersion("1.0.0");
        // 声明为泛化接口
        reference.setGeneric(true);

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
        Map<String, Object> person1 = new HashMap<>();
        person1.put("class", "cn.coder4j.study.example.dubbo.model.Person");
        person1.put("name", "kiwi");
        person1.put("age", "18");

        Map<String, Object> person2 = new HashMap<>();
        person2.put("class", "cn.coder4j.study.example.dubbo.model.Person");
        person2.put("name", "kiwi2");
        person2.put("age", "22");

        // 如果返回POJO将自动转成Map
        Object result = genericService.$invoke("findPersionList", new String[]
                {"java.util.List"}, new Object[]{Lists.newArrayList(person1, person2)});

        assertEquals(2, ((List) result).size());
    }

    /**
     * 通过泛化调用泛化实现
     */
    @Test
    public void testGenericServiceType() {

        // 引用远程服务
        reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface("cn.coder4j.study.example.dubbo.DemoService");
        reference.setVersion("1.0.0");
        // 声明为泛化接口
        reference.setGeneric(true);

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        String param = "world";
        Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{param});

        assertEquals("mock success", result.toString());
    }
}