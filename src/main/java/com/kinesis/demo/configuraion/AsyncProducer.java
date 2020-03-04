package com.kinesis.demo.configuraion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.KinesisClientUtil;

@Configuration
public class AsyncProducer {
    @Value("${aws.kinesis.region}")
    String region;

    @Bean
    public KinesisAsyncClient getKinesisAsyncClient() {
        return KinesisClientUtil.createKinesisAsyncClient(software.amazon.awssdk.services.kinesis.KinesisAsyncClient.builder().credentialsProvider(DefaultCredentialsProvider.create()).region(Region.of(this.region)));

    }

}
