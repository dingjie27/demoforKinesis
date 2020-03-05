package com.kinesis.demo.service.producer;

import com.alibaba.fastjson.JSON;
import com.kinesis.demo.pojo.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequestEntry;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
public class ProducerUsingKinesisAsyncClient {
    private final Logger log = LoggerFactory.getLogger(ProducerUsingKinesisAsyncClient.class);
    @Value("${aws.kinesis.stream.name}")
    String defaultStreamName;

    @Value("${aws.kinesis.region}")
    String region;
    @Autowired
    KinesisAsyncClient kinesisAsyncClient;

    //put records one by one
    public void putRecord() {
        Msg msg = new Msg();
        for (int i = 0; i < 10; i++) {
            msg.setMaintenance_end_date(new Date());
            String msgContent = JSON.toJSONString(msg);

            PutRecordRequest request = PutRecordRequest.builder()
                    .partitionKey(String.format("partitionKey-%d", i))
                    .streamName(defaultStreamName)
                    .data(SdkBytes.fromByteArray(msgContent.getBytes()))
                    .build();

            kinesisAsyncClient.putRecord(request);
            try {
                log.info("Producing record msg number= {} , record sequence number {} ", i, kinesisAsyncClient.putRecord(request).get().sequenceNumber());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    //put records in batch
    public void putRecords() {
        Collection<PutRecordsRequestEntry> records = new ArrayList<>();
        Msg msg = new Msg();
        for (int i = 0; i < 10; i++) {
            msg.setMaintenance_end_date(new Date());
            String msgContent = JSON.toJSONString(msg);

            records.add(PutRecordsRequestEntry.builder()
                    .data(SdkBytes.fromByteBuffer(ByteBuffer.wrap(msgContent.getBytes())))
                    .partitionKey(String.format("partitionKey-%d", i)).build());

        }
        PutRecordsRequest putRecordsRequest = PutRecordsRequest.builder().streamName(defaultStreamName)
                .records(records).build();
        kinesisAsyncClient.putRecords(putRecordsRequest);
        try {
            log.error("failed count =  {} ", kinesisAsyncClient.putRecords(putRecordsRequest).get().failedRecordCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
