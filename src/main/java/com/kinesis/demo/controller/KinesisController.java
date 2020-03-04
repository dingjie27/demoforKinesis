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

    @ApiOperation(value = "查看kinesis stream name", notes = "stream流的名称")
    @RequestMapping(value = "streamName", method = RequestMethod.GET)
    public String getStreamName() {
//       return producer.run();
        return null;
    }

    @RequestMapping(value = "gen", method = RequestMethod.GET)
    public void generateDataWithKinesisAsyncClient() {
        try {
            producerUsingKinesisAsyncClient.putRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
