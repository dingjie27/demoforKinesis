package com.kinesis.demo.service.fanoutconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumerRunner implements ApplicationRunner {
    @Autowired
    FanoutConsumer consumer;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
//        System.out.println("My fan out consumer is running.....");
//        consumer.run();
    }

}