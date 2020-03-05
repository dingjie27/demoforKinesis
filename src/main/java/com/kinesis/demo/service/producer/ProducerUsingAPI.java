package com.kinesis.demo.service.producer;

import com.alibaba.fastjson.JSON;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.services.kinesis.model.PutRecordsResultEntry;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.kinesis.demo.pojo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProducerUsingAPI {
    //public class ProducerUsingAPI implements Runnable {
    @Autowired
    AmazonKinesis amazonKinesis;
    @Autowired
    AmazonSQS sqsClient;
    @Value("${aws.kinesis.stream.name}")
    String defaultStreamName;

    @Value("${aws.sqs.url}")
    String sqsUrl;

    public void putRecord(String streamName) {
        PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        if (StringUtils.isEmpty(streamName)) {
            streamName = defaultStreamName;
        }
        putRecordsRequest.setStreamName(streamName);
        List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();
        Msg msg = new Msg();
        for (int i = 0; i < 300; i++) {
            msg.setMaintenance_end_date(new Date());
            String msgContent = JSON.toJSONString(msg);

            PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(msgContent.getBytes()));

            putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", i));
            putRecordsRequestEntryList.add(putRecordsRequestEntry);
        }

        //每X条数据提交一次，数据最好是25KB的整数倍
        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        PutRecordsResult putRecordsResult = amazonKinesis.putRecords(putRecordsRequest);

        System.out.println("Put Result" + putRecordsResult);


        //提交失败的数据放入sqs
        final List<SendMessageBatchRequestEntry> failedRecordsListForSqs = new ArrayList<>();
        if (putRecordsResult.getFailedRecordCount() > 0) {
            while (putRecordsResult.getFailedRecordCount() > 0) {
                final List<PutRecordsResultEntry> putRecordsResultEntryList = putRecordsResult.getRecords();
                for (int i = 0; i < putRecordsResultEntryList.size(); i++) {
                    final PutRecordsRequestEntry putRecordRequestEntry = putRecordsRequestEntryList.get(i);
                    final PutRecordsResultEntry putRecordsResultEntry = putRecordsResultEntryList.get(i);
                    if (putRecordsResultEntry.getErrorCode() != null) {
                        SendMessageBatchRequestEntry sendMessageBatchRequestEntry = new SendMessageBatchRequestEntry(String.valueOf(i), putRecordRequestEntry.getData().toString());
                        failedRecordsListForSqs.add(sendMessageBatchRequestEntry);
                    }
                }

            }
//            sendFailedMsgToSQS(failedRecordsListForSqs);
        }

    }


    //    将发送失败的消息统一发送到sqs中等待处理
    public void sendFailedMsgToSQS(List<SendMessageBatchRequestEntry> failedRecordsListForSqs) {

        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                .withQueueUrl(sqsUrl)
                .withEntries(failedRecordsListForSqs);
        sqsClient.sendMessageBatch(send_batch_request);
    }
// 多线程模式请自动修改bean的获取方式
//    @Override
//    public void run() {
//        putRecord(this.defaultStreamName);
//    }

}
