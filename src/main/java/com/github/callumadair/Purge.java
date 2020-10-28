package com.github.callumadair;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

/**
 * A class for mass deletion of messages in a discord server.
 *
 * @author Callum Adair
 * @version 0.1
 */
public class Purge {
    private Bot bot;

    /**
     * Creates a new instance of the class with the specified api and bot.
     *
     * @param bot the specified bot.
     */
    public Purge(Bot bot) {
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
        purgeMessages();
    }

    /**
     * Deletes the specified number of starting from the most recent.
     */
    private void purgeMessages() {
        bot.getApi().addMessageCreateListener(event -> {
            try {
                if (event.getMessageAuthor().isServerAdmin()
                        && event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "purge")) {
                    Scanner in = new Scanner(event.getMessageContent());
                    in.next();
                    int numOfMessages = in.nextInt() + 1;

                    MessageSet messages = event.getChannel().getMessages(numOfMessages).join();
                    for (Message message : messages) {
                        message.delete();
                    }
                    in.close();
                }
            } catch (InputMismatchException e) {
                event.getChannel().sendMessage("Please try again with a whole number.");
            } catch (NoSuchElementException e) {
                event.getChannel().sendMessage("Please specify a number of messages.");
            }

        });

    }
}