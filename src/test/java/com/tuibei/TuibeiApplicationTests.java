package com.tuibei;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TuibeiApplicationTests {

@Autowired
private Gson gson;

    @Test
    public void contextLoads()throws Exception {
       // sender.send("Hello");
        HashMap<String,String> map =new HashMap<>();
        map.put("1","v1");
        String s = gson.toJson(map);
        System.err.println(s);
    }

}
