package com.github.callumadair;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;

/**
 * Gets the current time on google.
 * 
 * @author Callum Adair
 * @version 0.1
 */
public class Time {
    private DiscordApi api;
    private Bot bot;

    /**
     * Creates a new instance of the class with the specified api and bot.
     * 
     * @param api the specified api.
     * @param bot the specified bot.
     */
    public Time(DiscordApi api, Bot bot) {
        setApi(api);
        setBot(bot);
    }

    /**
     * Changes the api to the one specified.
     * 
     * @param api the specified api.
     */
    private void setApi(DiscordApi api) {
        this.api = api;
    }

    /**
     * 
     * @return returns the current api.
     */
    public DiscordApi getApi() {
        return api;
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
     * 
     * @return returns the current bot.
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Implements the methods of the class.
     */
    public void listener() {
        getTime();
    }

    /**
     * Gets the current time in an embed.
     */
    private void getTime() {
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase(bot.getPrefix() + "time")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Time").setColor(bot.getRoleColour()).setUrl(
                        "https://www.google.com/search?q=time&rlz=1C1CHBD_en-GBGB828GB828&oq=time&aqs=chrome.0.69i59l3j69i60l2j69i61j69i65j69i60.638j0j7&sourceid=chrome&ie=UTF-8");
                event.getChannel().sendMessage(embed);
            }
        });
    }
}