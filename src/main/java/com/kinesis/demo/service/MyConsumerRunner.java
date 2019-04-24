package com.kinesis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyConsumerRunner implements ApplicationRunner {
    @Autowired
    Consumer consumer;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        System.out.println("MyConsumerRunner is running.....");
        consumer.run();

    }

}