package com.github.callumadair;

import com.github.callumadair.Bot.*;

/**
 * A class for finding the latency of the application.
 *
 * @author Callum Adair
 * @version 1.1
 */
public class Pong extends BotAction{

    /**
     * Creates a new instance of the class using the specified bot.
     *
     * @param bot the specified bot.
     */
    public Pong(Bot bot) {
        super(bot);
    }

    /**
     * Implements the methods of the class.
     */
    public void listener() {
        getPing();
    }

    /**
     * Responds to the ping message and calculates the latency of the response.
     */
    private void getPing() {
        getBot().getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "ping")) {
                long firstTime = event.getMessage().getCreationTimestamp().toEpochMilli();
                event.getChannel().sendMessage("Pong!");

                getBot().getApi().addMessageCreateListener(listen -> {
                    if (listen.getMessageContent().equals("Pong!")) {
                        long secondTime = listen.getMessage().getCreationTimestamp().toEpochMilli();
                        long ping = secondTime - firstTime;
                        listen.getMessage().edit("Pong! `" + ping + "ms`");
                    }
                });

            }

        });
    }
}