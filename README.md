# demoforKinesis
demoforKinesis KCL 2.0v

This is a sample java codes including exception handle mechanism,two kinds of consummers and Records producer in JAVA. The demo is using springboot framework.
The demo uses KCL2.0, which is the latest version of KCL， it does a lot of the heavy lifting common to consumer apps.

The architechture is as follow:
![image](https://github.com/dingjie27/demoforKinesis/blob/master/images/architecture.png)

1. There are two kinds of comsumers in Kinesis consumer.Enhanced fan-out is an Amazon Kinesis Data Streams feature that enables consumers to receive records from a data stream with dedicated throughput of up to 2 MB of data per second per shard,while in standard mode, 
2. Do note that KCL needs Dynamodb for checkpoint, so if you run this demo on EC2 with an IAM role, attach Dyamodb policies.
3. This demo use SQS as DLQ with an exponential backoff algorithm for downgrade handler, you can always customize your own downgrade handler.
