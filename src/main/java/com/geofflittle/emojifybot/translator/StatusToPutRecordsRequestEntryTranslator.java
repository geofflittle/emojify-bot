package com.geofflittle.emojifybot.translator;

import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.geofflittle.emojifybot.model.Status;
import com.google.gson.Gson;

import javax.inject.Inject;
import java.nio.ByteBuffer;

public class StatusToPutRecordsRequestEntryTranslator {

    private final Gson gson;

    @Inject
    public StatusToPutRecordsRequestEntryTranslator(Gson gson) {this.gson = gson;}

    public PutRecordsRequestEntry translate(Status status) {
        return new PutRecordsRequestEntry()
                .withPartitionKey(status.getId().toString())
                .withData(getByteBuffer(status));
    }

    private ByteBuffer getByteBuffer(Status status) {
        return ByteBuffer.wrap(gson.toJson(status).getBytes());
    }

}
