package com.github.callumadair;

import org.javacord.api.*;
import org.javacord.api.entity.message.embed.*;

import java.awt.*;

/**
 * A special class for Fung Wah.
 *
 * @author Callum Adair
 * @version 0.1
 */
public class FungWah {
    private Bot bot;

    /**
     * Creates a new instance of Fung Wah with the specified api and bot.
     *
     * @param bot the specified bot.
     */
    public FungWah(Bot bot) {
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
        bully();
    }


    /**
     * Bullies Fung Wah.
     */
    private void bully() {
        bot.getApi().addMessageCreateListener(event -> {
            if (event.getMessageAuthor().isBotOwner()
                    && event.getMessageContent().equalsIgnoreCase(bot.getPrefix() + "fungwah")) {
                EmbedBuilder fungWah = new EmbedBuilder();
                fungWah.setTitle("Look at this Fung Wah").setDescription("Pretty neat, huh?").setColor(Color.CYAN);
                event.getChannel().sendMessage(fungWah);
            }
        });
    }
}