package com.geofflittle.emojifybot.writer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.geofflittle.emojifybot.model.Status;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class QueueWriter {

    private final Gson gson;
    private final String queueUrl;
    private final AmazonSQS amazonSQS;

    @Inject
    public QueueWriter(Gson gson, @Named("queueUrl") String queueUrl, AmazonSQS amazonSQS) {
        this.gson = gson;
        this.queueUrl = queueUrl;
        this.amazonSQS = amazonSQS;
    }

    public void write(List<Status> retrieve) {
        SendMessageBatchRequest sendMessageBatchRequest = getSendMessageBatchRequest(retrieve);
        log.info(String.format("Writing %s", gson.toJson(sendMessageBatchRequest)));
        amazonSQS.sendMessageBatch(sendMessageBatchRequest);
    }

    private SendMessageBatchRequest getSendMessageBatchRequest(List<Status> retrieve) {
        return new SendMessageBatchRequest()
                .withQueueUrl(queueUrl)
                .withEntries(retrieve.stream()
                .map(this::getSendMessageBatchRequestEntry)
                .collect(Collectors.toList()));
    }

    private SendMessageBatchRequestEntry getSendMessageBatchRequestEntry(Status status) {
        return new SendMessageBatchRequestEntry()
                .withId(status.getId().toString())
                .withMessageBody(gson.toJson(status));
    }

}
