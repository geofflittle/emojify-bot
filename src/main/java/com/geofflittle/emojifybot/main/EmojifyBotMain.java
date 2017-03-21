package com.geofflittle.emojifybot.main;

import com.geofflittle.emojifybot.model.Status;
import com.geofflittle.emojifybot.module.EmojifyModule;
import com.geofflittle.emojifybot.reader.RecentTweetReader;
import com.geofflittle.emojifybot.writer.QueueWriter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.log4j.Log4j2;
import twitter4j.TwitterException;

import java.util.List;

@Log4j2
public class EmojifyBotMain {

    private static String QUEUE_NAME = "recent-tweets";

    public static void main(String[] args) throws TwitterException {
        Injector injector = Guice.createInjector(new EmojifyModule(QUEUE_NAME));
        RecentTweetReader recentTweetReader = injector.getInstance(RecentTweetReader.class);
        QueueWriter queueWriter = injector.getInstance(QueueWriter.class);
        List<Status> retrieve = recentTweetReader.read();
        log.info(retrieve);
        queueWriter.write(retrieve);
    }

}
