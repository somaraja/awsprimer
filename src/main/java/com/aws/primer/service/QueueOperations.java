package com.aws.primer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.aws.primer.dynamo.DailyEvents;
import com.aws.primer.dynamo.SiteNames;
import com.google.gson.Gson;

@Component
public class QueueOperations {
	@Value("${sqs.url}")
	private String sqsUrl;

	@Value("${aws.accessKey}")
	private String awsAccessKey;

	@Value("${aws.secretKey}")
	private String awsSecretKey;

	@Value("${aws.region}")
	private String awsRegion;

	private AmazonSQS getSQS() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		return AmazonSQSClientBuilder.standard().withRegion(awsRegion)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}

	private AmazonDynamoDB getDynamoDBClient() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		return AmazonDynamoDBClientBuilder.standard().withRegion(awsRegion)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}

	private void readMessagesFromSQS() {
		List<Message> messages = null;
		do {
			messages = getSQS().receiveMessage(sqsUrl).getMessages();
			for (final Message message : messages) {
				writeMessagesToDynamoDB(message.getBody());
				getSQS().deleteMessage(sqsUrl, message.getReceiptHandle());
			}
		} while (messages.size() > 0);
		System.out.println("Read from SQS is done");
	}
	
	private void writeMessagesToDynamoDB(String message) {
		AmazonDynamoDB client = getDynamoDBClient();
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client);
		DailyEvents dailyEvents = new Gson().fromJson(message, DailyEvents.class);

		dynamoDBMapper.save(dailyEvents);
		System.out.println("Write to DynamoDB is done");
	}

	public List<DailyEvents> readMessagesFromDynamoDB() {
		AmazonDynamoDB client = getDynamoDBClient();
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client);
		return dynamoDBMapper.scan(DailyEvents.class, new DynamoDBScanExpression());
	}

	public void writeMessageToSQS(String message) {
		SendMessageRequest send_msg_request = new SendMessageRequest().withQueueUrl(sqsUrl).withMessageBody(message)
				.withDelaySeconds(5);
		getSQS().sendMessage(send_msg_request);

		System.out.println("Write to SQS is done");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readMessagesFromSQS();
	}
	
	public List<SiteNames> getAllSites() {
		AmazonDynamoDB client = getDynamoDBClient();
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client);
		return dynamoDBMapper.scan(SiteNames.class, new DynamoDBScanExpression());
	}

}