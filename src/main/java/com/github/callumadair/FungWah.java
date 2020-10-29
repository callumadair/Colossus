package com.github.callumadair;

import com.github.callumadair.Bot.*;
import org.javacord.api.entity.message.embed.*;

import java.awt.*;

/**
 * A special class for Fung Wah.
 *
 * @author Callum Adair
 * @version 0.2
 */
public class FungWah extends BotAction{

    /**
     * Creates a new instance of Fung Wah with the specified api and bot.
     *
     * @param bot the specified bot.
     */
    public FungWah(Bot bot) {
        super(bot);
    }

    /**
     * Implements the methods of the class.
     */
    public void start() {
        bully();
    }


    /**
     * Bullies Fung Wah.
     */
    private void bully() {
        getBot().getApi().addMessageCreateListener(event -> {
            if (event.getMessageAuthor().isBotOwner()
                    && event.getMessageContent().equalsIgnoreCase(getBot().getPrefix()+ "fungwah")) {
                EmbedBuilder fungWah = new EmbedBuilder();
                fungWah.setTitle("Look at this Fung Wah").setDescription("Pretty neat, huh?").setColor(Color.CYAN);
                event.getChannel().sendMessage(fungWah);
            }
        });
    }
}