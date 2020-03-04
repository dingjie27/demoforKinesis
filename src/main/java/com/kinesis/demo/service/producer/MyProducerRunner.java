package com.kinesis.demo.service.producer;

import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
//public class MyProducerRunner implements CommandLineRunner {
    public class MyProducerRunner {
//    @Override
    public void run() throws Exception {
        System.out.println("MyProducerRunner is running.....");
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, queue);
        for (int i = 0; i < 5; i++) {
            ProducerUsingAPI myTask = new ProducerUsingAPI();
            pool.execute(myTask);
            System.out.println("线程池中的线程数目：" + pool.getPoolSize() + ",队列中等待执行的任务数量：" + pool.getQueue().size() + ",已执行完的任务数目：" + pool.getCompletedTaskCount());
        }
        pool.shutdown();
    }
}
