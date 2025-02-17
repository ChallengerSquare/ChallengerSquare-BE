package com.ssafy.challs.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${cloud.aws.s3.credentials.access-key}")
	private String accessKey;

	@Value("${cloud.aws.s3.credentials.secret-key}")
	private String secretKey;

	@Value("${cloud.aws.s3.region}")
	private String region;

	@Bean
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return (AmazonS3Client)AmazonS3ClientBuilder.standard().withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
			.build();
	}

}
