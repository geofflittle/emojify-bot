package com.geofflittle.emojifybot.module;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import lombok.Data;
import lombok.NonNull;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Data
public class EmojifyModule extends AbstractModule {

//    @NonNull
//    private final String queueName;

    @NonNull
    private final String streamName;

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("streamName")).toInstance(streamName);
    }

    @Provides
    @Singleton
    public Twitter twitter() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter;
    }

    @Provides
    @Singleton
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new PropertiesFileCredentialsProvider("src/main/resources/aws-credentials.properties");
    }

    @Provides
    @Singleton
    public AmazonKinesis amazonKinesis(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonKinesisClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.US_EAST_1)
                .build();
    }

//    @Provides
//    @Singleton
//    public AmazonSQS amazonSQS(AWSCredentialsProvider awsCredentialsProvider) {
//        return AmazonSQSClientBuilder.standard()
//                .withCredentials(awsCredentialsProvider)
//                .withRegion(Regions.US_EAST_1)
//                .build();
//    }
//
//    @Provides
//    @Named("queueUrl")
//    public String queueUrl(AmazonSQS amazonSQS) {
//        return amazonSQS.getQueueUrl(queueName).getQueueUrl();
//    }

}
