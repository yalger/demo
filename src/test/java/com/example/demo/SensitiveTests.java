package com.example.demo;

import com.example.demo.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = DemoApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里可以赌博，可以嫖娼，可以吸毒，可以开票，哈哈哈！";
        System.out.println(sensitiveFilter.filter(text));

        text = "这里可以☆赌☆博☆，可以☆嫖☆娼☆，可以☆吸☆毒☆，可以☆开☆票☆，哈哈哈！";
        System.out.println(sensitiveFilter.filter(text));

        text = "123fabc";
        System.out.println(sensitiveFilter.filter(text));
    }
}
