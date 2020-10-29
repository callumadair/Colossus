package com.github.callumadair.data;

import com.github.callumadair.Bot.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;

/**
 * Gets the current time on google.
 *
 * @author Callum Adair
 * @version 0.2
 */
public class Time extends BotAction {

    /**
     * Creates a new instance of the class with the specified bot.
     *
     * @param bot the specified bot.
     */
    public Time(Bot bot) {
        super(bot);
    }

    /**
     * Implements the methods of the class.
     */
    public void start() {
        getTime();
    }

    /**
     * Gets the current time in an embed.
     */
    private void getTime() {
        getBot().getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "time")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Time").setColor(getBot().getRoleColour()).setUrl(
                        "https://www.google.com/search?q=time&rlz=1C1CHBD_en-GBGB828GB828&oq=time&aqs=chrome" +
                                ".0.69i59l3j69i60l2j69i61j69i65j69i60.638j0j7&sourceid=chrome&ie=UTF-8");
                event.getChannel().sendMessage(embed);
            }
        });
    }
}