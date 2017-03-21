package com.geofflittle.emojifybot.module;

import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.Data;
import lombok.NonNull;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Data
public class EmojifyModule extends AbstractModule {

    @NonNull
    private final String queueName;

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Twitter twitter() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter;
    }

    @Provides
    @Singleton
    public AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder.standard()
                .withCredentials(new PropertiesFileCredentialsProvider("src/main/resources/aws-credentials.properties"))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    @Provides
    @Named("queueUrl")
    public String queueUrl(AmazonSQS amazonSQS) {
        return amazonSQS.getQueueUrl(queueName).getQueueUrl();
    }

}
