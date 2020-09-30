# demoforKinesis
demoforKinesis KCL 2.0v

This is a sample java codes including exception handle mechanism,two kinds of consummers and Records producer in JAVA. The demo is using springboot framework.
The demo uses KCL2.0, which is the latest version of KCL， it does a lot of the heavy lifting common to consumer apps.

The architechture is as follow:
![image](https://github.com/dingjie27/demoforKinesis/blob/master/images/architecture.png)

1. There are two kinds of comsumers in Kinesis consumer.Enhanced fan-out is an Amazon Kinesis Data Streams feature that enables consumers to receive records from a data stream with dedicated throughput of up to 2 MB of data per second per shard,while in standard mode, 
2. Do note that KCL needs Dynamodb for checkpoint, so if you run this demo on EC2 with an IAM role, attach Dyamodb policies.
3. This demo use SQS as DLQ with an exponential backoff algorithm for downgrade handler, you can always customize your own downgrade handler.


代码解读：
1、生产者核心代码：https://github.com/dingjie27/demoforKinesis/blob/master/src/main/java/com/kinesis/demo/service/producer/ProducerUsingKinesisAsyncClient.java
通过kinesisAsyncClient来进行record提交，demo中每次提交10条。如果需要自定义record的内容，请自行修改Msg类。

2、fan-out模式消费者核心代码：https://github.com/dingjie27/demoforKinesis/blob/master/src/main/java/com/kinesis/demo/service/fanoutconsumer/FanoutRecordProcessor.java
如果需要使用增强型扇出的消费者，请参考以上链接。fan-out的消费者比标准的消费者性能更强，如果需要自定义消费逻辑，请修改processRecords()这个方法的代码

3、标准模式消费者核心代码：https://github.com/dingjie27/demoforKinesis/blob/master/src/main/java/com/kinesis/demo/service/standardconsumer/StandardRecordProcessor.java

4、如果项目需要启动时就自动加载producer和consumer请修改
https://github.com/dingjie27/demoforKinesis/blob/master/src/main/java/com/kinesis/demo/service/standardconsumer/StandardConsumer.java
https://github.com/dingjie27/demoforKinesis/blob/master/src/main/java/com/kinesis/demo/service/standardconsumer/StandardConsumerRunner.java
以及producer路径下的以上两个方法，通过spring框架实现自动启动。
