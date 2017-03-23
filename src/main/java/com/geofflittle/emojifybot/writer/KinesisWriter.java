package com.geofflittle.emojifybot.writer;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class KinesisWriter {

    private final AmazonKinesis amazonKinesis;

    @Inject
    public KinesisWriter(AmazonKinesis amazonKinesis) {this.amazonKinesis = amazonKinesis;}

    public <X> void write(String streamName, List<X> xs, Function<X, PutRecordsRequestEntry> translator) {
        PutRecordsRequest putRecordsRequest = getPutRecordsRequest(streamName, xs.stream()
            .map(translator::apply)
            .collect(Collectors.toList()));
        amazonKinesis.putRecords(putRecordsRequest);
    }

    private PutRecordsRequest getPutRecordsRequest(String streamName,
            List<PutRecordsRequestEntry> putRecordsRequestEntries) {
        return new PutRecordsRequest()
                .withStreamName(streamName)
                .withRecords(putRecordsRequestEntries);
    }

}
