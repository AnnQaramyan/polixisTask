package com.test;

import com.test.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(MainApplication.class, args);
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");

        try {
            userService.save();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}