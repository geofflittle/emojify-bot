package com.geofflittle.emojifybot.reader;

import com.geofflittle.emojifybot.model.Status;
import com.geofflittle.emojifybot.translator.StatusTranslator;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class RecentTweetReader {

    private final Twitter twitter;
    private final StatusTranslator statusTranslator;

    @Inject
    public RecentTweetReader(Twitter twitter, StatusTranslator statusTranslator) {
        this.twitter = twitter;
        this.statusTranslator = statusTranslator;
    }

    public List<Status> read() throws TwitterException {
        return twitter.timelines().getMentionsTimeline().stream()
                .map(statusTranslator::translate)
                .collect(Collectors.toList());
    }
}
