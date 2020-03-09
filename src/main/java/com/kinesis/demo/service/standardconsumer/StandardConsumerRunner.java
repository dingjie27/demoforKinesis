package com.kinesis.demo.service.standardconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StandardConsumerRunner implements ApplicationRunner {
    @Autowired
    StandardConsumer consumer;
    private static final Logger log = LoggerFactory.getLogger(StandardConsumerRunner.class);

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        /**
         * open this if you want your consumer to run immediately after the project is started
         */
//        log.info("My standard consumer is running.....");
//        consumer.startConsume();
    }
}