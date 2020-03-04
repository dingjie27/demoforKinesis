package com.kinesis.demo.service.producer;

import com.alibaba.fastjson.JSON;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.kinesis.demo.pojo.Msg;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.kinesis.common.KinesisClientUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Service
public class ProducerUsingKinesisAsyncClient {
    @Value("${aws.kinesis.stream.name}")
    String defaultStreamName;

    @Value("${aws.kinesis.region}")
    String region;
    @Autowired
    KinesisAsyncClient kinesisAsyncClient;

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
                System.out.println("msg number= " + i + " record sequence number " + kinesisAsyncClient.putRecord(request).get().sequenceNumber());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
    public void putRecords() {
        KinesisAsyncClient kinesisAsyncClient= KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient.builder().credentialsProvider(DefaultCredentialsProvider.create()).region(Region.of("us-east-1")));
        List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Msg msg = new Msg();
            msg.setMaintenance_end_date(new Date());
            String msgContent = JSON.toJSONString(msg);

            PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(msgContent.getBytes()));

            putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", i));
            putRecordsRequestEntryList.add(putRecordsRequestEntry);
        }

        PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        putRecordsRequest.setStreamName(defaultStreamName);
         kinesisAsyncClient.putRecords(putRecordsRequest);
    }


}
