package com.tuibei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.tuibei.mapper")
public class TuibeiApplication {




    public static void main(String[] args) {
      SpringApplication.run(TuibeiApplication.class, args);

    }




}
