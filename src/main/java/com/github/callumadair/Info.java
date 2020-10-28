package com.github.callumadair;

import org.javacord.api.*;
import org.javacord.api.entity.message.embed.*;
import org.javacord.api.entity.server.*;
import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.time.temporal.*;
import java.util.*;

/**
 * Allows the collection of information on a variety of different applications.
 *
 * @author Callum Adair
 * @version 0.1
 */
public class Info {
    private DiscordApi api;
    private Bot bot;

    public Info(DiscordApi api, Bot bot) {
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
     * @return returns the current bot.
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Implements the methods of the Info class.
     */
    public void listener() {
        api.addMessageCreateListener(event -> {
            serverInfo(event);
            botInfo(event);
        });

    }

    /**
     * Gets basic infromation about the bot user account in the event server.
     *
     * @param event the specified event.
     */
    private void botInfo(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(bot.getPrefix() + "binfo")) {
            EmbedBuilder botInfo = new EmbedBuilder();
            User colossus = api.getYourself();

            botInfo.setTitle("Bot Info").setColor(bot.getRoleColour()).setDescription(api.getYourself().getName())
                    .addField("Servers", ":robot: " + colossus.getName() + " is present in "
                            + Integer.toString(colossus.getMutualServers().size()) + " servers");

            event.getChannel().sendMessage(botInfo);
        }
    }

    /**
     * Gets basic information about the specified event server.
     *
     * @param event the specified event
     */
    private void serverInfo(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(bot.getPrefix() + "sinfo")) {
            EmbedBuilder serverInfo = new EmbedBuilder();
            Server server = event.getServer().get();

            serverInfo.setTitle("Server Info").setColor(bot.getRoleColour()).setDescription(server.getName()).addField(
                    "Members",
                    ":two_men_holding_hands: " + Integer.toString(server.getMemberCount()) + " members (:robot: "
                            + Integer.toString(getBotCount(server)) + " bots), " + getOnlineUsers(server) + " online.")
                    .addField("Channels",
                            (server.getChannels().size() - server.getChannelCategories().size()) + " channels")
                    .addField("Owner", server.getOwner().get().getMentionTag()).addField("Creation Date",
                    server.getCreationTimestamp().truncatedTo(ChronoUnit.MINUTES).toString());

            event.getChannel().sendMessage(serverInfo);
        }

    }

    /**
     * Calculates and returns the number of bot users that are members of the
     * specified server.
     *
     * @param server the specified server.
     * @return returns the number of bots in the server.
     */
    public int getBotCount(Server server) {
        int botCount = 0;
        Collection<User> serverMembers = server.getMembers();
        for (User user : serverMembers) {
            if (user.isBot()) {
                ++botCount;
            }
        }
        return botCount;
    }

    /**
     * Calculates and returns the number of members of the specified server that are
     * online.
     *
     * @param server the specified server.
     * @return returns the number of online members.
     */
    public int getOnlineUsers(Server server) {
        int onlineCount = 0;
        Collection<User> serverMembers = server.getMembers();
        for (User user : serverMembers) {
            if (user.getStatus().equals(UserStatus.ONLINE)) {
                ++onlineCount;
            }
        }
        return onlineCount;
    }
}