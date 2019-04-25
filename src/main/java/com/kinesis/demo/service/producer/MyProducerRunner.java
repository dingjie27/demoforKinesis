package com.kinesis.demo.service.producer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyProducerRunner implements ApplicationRunner {

    @Override
    //需要多线程模式可自行修改
    public void run(ApplicationArguments var1) throws Exception {
//        System.out.println("MyProducerRunner is running.....");
//        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, queue);
//        for (int i = 0; i < 5; i++) {
//            Producer myTask = new Producer();
//            pool.execute(myTask);
//            System.out.println("线程池中的线程数目：" + pool.getPoolSize() + ",队列中等待执行的任务数量：" + pool.getQueue().size() + ",已执行完的任务数目：" + pool.getCompletedTaskCount());
//        }
//        pool.shutdown();
    }
}
