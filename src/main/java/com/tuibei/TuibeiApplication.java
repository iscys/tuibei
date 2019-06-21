package com.tuibei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.tuibei.mapper")
public class TuibeiApplication {




    public static void main(String[] args) {
      SpringApplication.run(TuibeiApplication.class, args);
    }




}
