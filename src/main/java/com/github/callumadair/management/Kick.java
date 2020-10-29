package com.github.callumadair.management;

import com.github.callumadair.Bot.*;
import org.javacord.api.entity.server.*;
import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.util.*;

/**
 * The type Kick.
 */
public class Kick extends BotAction {
    /**
     * Instantiates a new Kick action.
     *
     * @param bot the specified bot
     */
    protected Kick(Bot bot) {
        super(bot);
    }

    @Override
    public void start() {
        getBot().getApi().addMessageCreateListener(event -> {
            if (bot.isBotModerator(Objects.requireNonNull(event.getMessageAuthor().asUser().orElse(null)),
                    event.getServer().orElse(null))
                    && event.getMessageContent().split(" ")[0]
                    .equalsIgnoreCase(getBot().getPrefix() + "kick")) {
                kickByMention(event);
                kickByMention(event, event.getMessageContent().split(" ")
                        [event.getMessageContent().split(" ").length - 1]);
                kickByID(event);
            }
        });
    }

    /**
     * Kicks the mentioned user(s) from the server of the specified event.
     *
     * @param event the specified event.
     */
    private void kickByMention(MessageCreateEvent event) {
        List<User> kicks = event.getMessage().getMentionedUsers();
        Server server = event.getServer().orElse(null);
        assert server != null;
        for (User kick : kicks) {
            server.kickUser(kick);
        }
    }

    /**
     * Kicks the mentioned user(s) from the server of the specified event.
     *
     * @param event  the specified event.
     * @param reason the reason given for kicking.
     */
    private void kickByMention(MessageCreateEvent event, String reason) {
        List<User> kicks = event.getMessage().getMentionedUsers();
        Server server = event.getServer().orElse(null);
        assert server != null;
        for (User kick : kicks) {
            server.kickUser(kick, reason);
        }
    }

    private void kickByID(MessageCreateEvent event) {
    }


}
