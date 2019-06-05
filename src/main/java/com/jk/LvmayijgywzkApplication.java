package com.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.jk.dao")
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
public class LvmayijgywzkApplication {

    public static void main(String[] args) {
        SpringApplication.run(LvmayijgywzkApplication.class, args);
    }

}
