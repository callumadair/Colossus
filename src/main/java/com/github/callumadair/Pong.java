package com.github.callumadair;

import org.javacord.api.DiscordApi;

/**
 * A class for finding the latency of the application.
 *
 * @author Callum Adair
 * @version 1.0
 */
public class Pong {
    private Bot bot;

    /**
     * Creates a new instance of the class using the specifed api and bot.
     *
     * @param bot the specified bot.
     */
    public Pong(Bot bot) {
        setBot(bot);
    }

    /**
     * Changes the bot to the one specified.
     *
     * @param bot the specified bot.
     */
    private void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * @return returns the current bot.
     */
    public Bot getBot() {
        return bot;
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
        bot.getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase(bot.getPrefix() + "ping")) {
                Long firstTime = event.getMessage().getCreationTimestamp().toEpochMilli();
                event.getChannel().sendMessage("Pong!");

                bot.getApi().addMessageCreateListener(listen -> {
                    if (listen.getMessageContent().equals("Pong!")) {
                        Long secondTime = listen.getMessage().getCreationTimestamp().toEpochMilli();
                        Long ping = secondTime - firstTime;
                        listen.getMessage().edit("Pong! `" + ping + "ms`");
                    }
                });

            }

        });
    }
}