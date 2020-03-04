package com.kinesis.demo.service.fanoutconsumer;

import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;

public class FanoutRecordProcessorFactory implements ShardRecordProcessorFactory {
    public ShardRecordProcessor shardRecordProcessor() {
        return new FanoutRecordProcessor();
    }
}