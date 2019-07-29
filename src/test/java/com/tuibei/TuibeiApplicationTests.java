package com.tuibei;

import com.google.gson.Gson;
import com.tuibei.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jnlp.UnavailableServiceException;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TuibeiApplicationTests {

@Autowired
private ApplicationContext ApplicationContext;

    @Test
    public void contextLoads()throws Exception {
        String[] beanDefinitionNames = ApplicationContext.getBeanDefinitionNames();

        for(String s:beanDefinitionNames){
            System.out.println(s);
        }

    }

}
