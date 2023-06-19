package com.yxq.serviceedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@EnableFeignClients    //服务调用
@EnableDiscoveryClient  //nacos注册
@ComponentScan(basePackages = {"com.yxq"}) //扫描依赖的jar包 common中的service_base
@SpringBootApplication
@MapperScan(basePackages = {"com.yxq.serviceedu.mapper"}) //扫描到mapper接口，实现动态代理
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
