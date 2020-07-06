package com.jw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.jw")
@MapperScan("com.jw.*.dao")
@ImportResource(locations = "classpath:applicationContext.xml")
@ServletComponentScan
public class AdminApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
