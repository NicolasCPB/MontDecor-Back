package com.mont.decor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfig {
	
	@Value("${aws.region}")
	private String awsRegion;
	
	@Value("${aws.access-key-id}")
	private String accessKey;
	
	@Value("${aws.secret-key-id}")
	private String secretKey;

    @Bean
    AmazonS3 createS3Instance() {
    	BasicAWSCredentials credential = new BasicAWSCredentials(accessKey, secretKey);
    	
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credential))
				.withRegion(awsRegion)
				.build();
	}
}
