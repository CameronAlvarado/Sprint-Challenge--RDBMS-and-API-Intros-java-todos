package com.lambdaschool.sprint2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Sprint2Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(Sprint2Application.class, args);
    }

}
