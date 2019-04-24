package com.kinesis.demo.controller;

import com.kinesis.demo.service.producer.Producer;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class KinesisController {
    @Autowired
    Producer producer;


    @ApiOperation(value = "提交kinesis stream name", notes = "stream流的名称")
    @ApiImplicitParam(name = "streamName", value = "streamName", dataType = "String", paramType = "path",required = true)
    @RequestMapping(value = "generatedata/{streamName}", method = RequestMethod.POST)
    public void KinesisController(@PathVariable String streamName) {
        producer.putRecord(streamName);
    }

}
