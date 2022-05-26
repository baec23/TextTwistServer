package com.baec.texttwist;

import com.baec.texttwist.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TexttwistApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(TexttwistApplication.class, args);
    }

    CommandLineRunner run(UserService userService)
    {
        return args -> {
          userService.saveRole(new Role())
        };
    }

}
