package com.github.callumadair.management;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.github.callumadair.Bot.*;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

/**
 * A class for mass deletion of messages in a discord server.
 *
 * @author Callum Adair
 * @version 0.2
 */
public class Purge extends BotAction {

    /**
     * Creates a new instance of the class with the specified bot.
     *
     * @param bot the specified bot.
     */
    public Purge(Bot bot) {
        super(bot);
    }

    /**
     * Implements the methods of the class.
     */
    public void start() {
        purgeMessages();
    }

    /**
     * Deletes the specified number of starting from the most recent.
     */
    private void purgeMessages() {
        getBot().getApi().addMessageCreateListener(event -> {
            try {
                if (event.getMessageAuthor().isServerAdmin()
                        && event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "purge")) {
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