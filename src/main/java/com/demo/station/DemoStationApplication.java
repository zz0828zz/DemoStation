package com.demo.station;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author lipb
 **/
@SpringBootApplication
@MapperScan("com.demo.station.mapper")
@EnableSwagger2
public class DemoStationApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoStationApplication.class,args);
    }
}
