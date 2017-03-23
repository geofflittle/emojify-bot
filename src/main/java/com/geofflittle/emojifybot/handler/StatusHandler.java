package com.geofflittle.emojifybot.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.google.gson.Gson;

import java.nio.charset.Charset;

public class StatusHandler implements RequestHandler<KinesisEvent, Void> {

    private static final Gson GSON = new Gson();

    @Override
    public Void handleRequest(KinesisEvent input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Lambda start");
        try {
            logger.log(String.format("input=%s", GSON.toJson(input)));
            input.getRecords().forEach(ker ->
                    logger.log(new String(ker.getKinesis().getData().array(), Charset.forName("UTF-8"))));
        } catch (Throwable t) {
            logger.log(String.format("Caught throwable %s", t.getMessage()));
        } finally {
            logger.log("Lambda end");
            return null;
        }
    }

}
