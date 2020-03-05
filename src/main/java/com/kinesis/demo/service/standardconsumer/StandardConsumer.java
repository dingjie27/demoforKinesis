package com.kinesis.demo.service.standardconsumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.common.InitialPositionInStream;
import software.amazon.kinesis.common.InitialPositionInStreamExtended;
import software.amazon.kinesis.common.KinesisClientUtil;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

import java.util.UUID;

@Component
public class StandardConsumer {

    private static final Logger log = LoggerFactory.getLogger(StandardConsumer.class);
    private static String streamName = "my-test-kinesis-data-stream";
    private static String applicationName = "standard-consumer";
    Region region = Region.US_EAST_1;
    private final KinesisAsyncClient kinesisAsyncClient;

    public StandardConsumer(KinesisAsyncClient kinesisAsyncClient) {
        this.kinesisAsyncClient = KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient.builder().region(this.region));
    }

    /**
     * KCL 2.x supports enhanced fan-out and polling as retrieval options.
     * Fan-out is by default enabled.
     * You can set the RetrievalSpecificConfig as PollingConfig in the RetrievalConfig, to opt-out of enhanced fan-out.
     */
    public void startConsume() {

        DynamoDbAsyncClient dynamoClient = DynamoDbAsyncClient.builder().region(region).build();
        CloudWatchAsyncClient cloudWatchClient = CloudWatchAsyncClient.builder().region(region).build();
        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, applicationName, kinesisAsyncClient, dynamoClient, cloudWatchClient, UUID.randomUUID().toString(), new StandardRecordProcessorFactory());
        Scheduler scheduler = new Scheduler(
                configsBuilder.checkpointConfig(),
                configsBuilder.coordinatorConfig(),
                configsBuilder.leaseManagementConfig(),
                configsBuilder.lifecycleConfig(),
                configsBuilder.metricsConfig(),
                configsBuilder.processorConfig(),
                configsBuilder.retrievalConfig().retrievalSpecificConfig(new PollingConfig(streamName, kinesisAsyncClient)).initialPositionInStreamExtended(InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON))
        );

        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.setDaemon(true);
        schedulerThread.start();
    }
}

