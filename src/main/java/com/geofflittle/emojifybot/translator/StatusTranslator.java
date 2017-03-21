package com.geofflittle.emojifybot.translator;

import com.geofflittle.emojifybot.model.Status;

public class StatusTranslator {

    public Status translate(twitter4j.Status status) {
        return Status.builder()
                .id(status.getId())
                .text(status.getText())
                .build();
    }

}
