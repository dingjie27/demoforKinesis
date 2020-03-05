package com.kinesis.demo.service.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
//public class MyProducerRunner implements CommandLineRunner {
/**
 * Caution！
 * If you wanna use producer in a multi-thread model, pls modify this class as you need.
 */

public class MyProducerRunner {
    private final Logger log = LoggerFactory.getLogger(MyProducerRunner.class);

    //    @Override
    public void run() throws Exception {
        System.out.println("MyProducerRunner is running.....");
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, queue);
        for (int i = 0; i < 5; i++) {
            ProducerUsingAPI myTask = new ProducerUsingAPI();
//            pool.execute(myTask);
            log.info("线程池中的线程数目：" + pool.getPoolSize() + ",队列中等待执行的任务数量：" + pool.getQueue().size() + ",已执行完的任务数目：" + pool.getCompletedTaskCount());
        }
        pool.shutdown();
    }
}
