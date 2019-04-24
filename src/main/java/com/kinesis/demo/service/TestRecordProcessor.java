package com.kinesis.demo.service;


import software.amazon.kinesis.exceptions.InvalidStateException;
import software.amazon.kinesis.exceptions.ShutdownException;
import software.amazon.kinesis.lifecycle.events.*;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.retrieval.KinesisClientRecord;

import java.util.Iterator;

public class TestRecordProcessor implements ShardRecordProcessor {
    private String shardId = "shardId-000000000001";


    @Override
    public void initialize(InitializationInput initializationInput) {
        shardId = initializationInput.shardId();
        initializationInput.pendingCheckpointSequenceNumber();
    }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        Iterator<KinesisClientRecord> it1 = processRecordsInput.records().iterator();
        while (it1.hasNext()) {

            System.out.println("processing record...the partitionKey is" + it1.next().partitionKey());

        }

        try {
            processRecordsInput.checkpointer().checkpoint();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        } catch (ShutdownException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void leaseLost(LeaseLostInput leaseLostInput) {

    }

    @Override
    public void shardEnded(ShardEndedInput shardEndedInput) {

    }

    @Override
    public void shutdownRequested(ShutdownRequestedInput shutdownRequestedInput) {

    }
}
