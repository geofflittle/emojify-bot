package com.geofflittle.emojifybot.runner;

import com.geofflittle.emojifybot.model.Status;
import com.geofflittle.emojifybot.writer.StatusKinesisWriter;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Collections;

public class TestKinesisRunner {

    private final StatusKinesisWriter statusKinesisWriter;

    @Inject
    public TestKinesisRunner(StatusKinesisWriter statusKinesisWriter) {this.statusKinesisWriter = statusKinesisWriter;}

    public void run(int cycles) {
        for (int i = 0; i < cycles; i++) {
            Status status = getNow();
            statusKinesisWriter.write(Collections.singletonList(status));
        }
    }

    private Status getNow() {
        long now = Instant.now().toEpochMilli();
        return Status.builder()
                .id(now)
                .text(String.format("test-%s", now))
                .build();
    }

}
