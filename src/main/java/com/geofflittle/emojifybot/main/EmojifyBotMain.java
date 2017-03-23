package com.geofflittle.emojifybot.main;

import com.geofflittle.emojifybot.module.EmojifyModule;
import com.geofflittle.emojifybot.runner.TestKinesisRunner;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.log4j.Log4j2;
import twitter4j.TwitterException;

@Log4j2
public class EmojifyBotMain {

    //    private static String QUEUE_NAME = "recent-tweets";
    private static String STREAM_NAME = "recent-tweets";

    public static void main(String[] args) throws TwitterException {
        Injector injector = Guice.createInjector(new EmojifyModule(STREAM_NAME));
        TestKinesisRunner testKinesisRunner = injector.getInstance(TestKinesisRunner.class);
        testKinesisRunner.run(10);
    }

}
