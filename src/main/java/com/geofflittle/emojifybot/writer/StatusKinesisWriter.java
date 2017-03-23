package com.geofflittle.emojifybot.writer;

import com.geofflittle.emojifybot.model.Status;
import com.geofflittle.emojifybot.translator.StatusToPutRecordsRequestEntryTranslator;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Log4j2
public class StatusKinesisWriter {

    private final String streamName;
    private final KinesisWriter kinesisWriter;
    private final StatusToPutRecordsRequestEntryTranslator statusToPutRecordsRequestEntryTranslator;

    @Inject
    public StatusKinesisWriter(@Named("streamName") String streamName, KinesisWriter kinesisWriter,
            StatusToPutRecordsRequestEntryTranslator statusToPutRecordsRequestEntryTranslator) {
        this.streamName = streamName;
        this.kinesisWriter = kinesisWriter;
        this.statusToPutRecordsRequestEntryTranslator = statusToPutRecordsRequestEntryTranslator;
    }

    public void write(List<Status> statuses) {
        log.trace("Writing {}", statuses);
        kinesisWriter.write(streamName, statuses, statusToPutRecordsRequestEntryTranslator::translate);
    }
}
