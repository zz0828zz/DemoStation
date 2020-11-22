package com.demo.station;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author lipb
 **/
@SpringBootApplication
@MapperScan("com.demo.station.mapper")
public class DemoStationApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoStationApplication.class,args);
    }
}
