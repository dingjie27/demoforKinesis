package com.kinesis.demo.controller;

import com.kinesis.demo.service.producer.MyProducerRunner;
import com.kinesis.demo.service.producer.ProducerUsingAPI;
import com.kinesis.demo.service.producer.ProducerUsingKinesisAsyncClient;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KinesisController {
    @Autowired
    ProducerUsingAPI producerUsingAPI;
    @Autowired
    MyProducerRunner myProducerRunner;
    @Autowired
    ProducerUsingKinesisAsyncClient producerUsingKinesisAsyncClient;

    @ApiOperation(value = "提交kinesis stream name", notes = "stream流的名称")
    @ApiImplicitParam(name = "streamName", value = "streamName", dataType = "String", paramType = "path",required = true)
    @RequestMapping(value = "generatedata/{streamName}", method = RequestMethod.POST)
    public void produceRecord(@PathVariable String streamName) {
        producerUsingAPI.putRecord(streamName);
    }


    @ApiOperation(value = "使用KinesisAsyncClient批量上传数据", notes = "批量性能更好")
    @RequestMapping(value = "batchput", method = RequestMethod.GET)
    public void batchPutRecordsUsingKinesisAsyncClient() {
        try {
            producerUsingKinesisAsyncClient.putRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "使用KinesisAsyncClient逐条上传数据", notes = "依次")
    @RequestMapping(value = "singleput", method = RequestMethod.GET)
    public void singlePutRecordUsingKinesisAsyncClient() {
        try {
            producerUsingKinesisAsyncClient.putRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
