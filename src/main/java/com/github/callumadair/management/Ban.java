package com.github.callumadair.management;

import com.github.callumadair.Bot.*;
import org.javacord.api.entity.server.*;
import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.util.*;

/**
 * The type Ban.
 */
public class Ban extends BotAction {
    private final int MESSAGE_DELETE_DEFAULT = 30;

    /**
     * Instantiates a new Ban object.
     *
     * @param bot the specified bot
     */
    protected Ban(Bot bot) {
        super(bot);
    }

    @Override
    public void start() {
        getBot().getApi().addMessageCreateListener(event -> {
            if (bot.isBotModerator(Objects.requireNonNull(event.getMessageAuthor().asUser().orElse(null)),
                    event.getServer().orElse(null))) {
                if (event.getMessageContent().split(" ")[0]
                        .equalsIgnoreCase(getBot().getPrefix() + "ban")) {
                    banByMention(event);
                    banByMention(event, event.getMessageContent().split(" ")
                            [event.getMessageContent().split(" ").length - 1]);
                    banByID(event);
                }
            }
        });
    }

    /**
     * Bans the mentioned user(s) from the server of the specified event.
     *
     * @param event the specified event.
     */
    private void banByMention(MessageCreateEvent event) {
        List<User> bans = event.getMessage().getMentionedUsers();
        Server server = event.getServer().orElse(null);
        assert server != null;
        for (User ban : bans) {
            server.banUser(ban);
        }
    }

    /**
     * Bans the mentioned user(s) from the server of the specified event.
     *
     * @param event  the specified event.
     * @param reason the reason given for the ban.
     */
    private void banByMention(MessageCreateEvent event, String reason) {
        List<User> bans = event.getMessage().getMentionedUsers();
        Server server = event.getServer().orElse(null);
        assert server != null;
        for (User ban : bans) {
            server.banUser(ban, MESSAGE_DELETE_DEFAULT, reason);
        }
    }

    private void banByID(MessageCreateEvent event) {
    }
}
