package com.geofflittle.emojifybot.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Status {

    @NonNull
    private final Long id;
    @NonNull
    private final String text;

}
