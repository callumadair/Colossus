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
                    banByID(event);
                }
            }
        });
    }

    private void banByMention(MessageCreateEvent event) {
        List<User> bans = event.getMessage().getMentionedUsers();
        Server server = event.getServer().orElse(null);
        assert server != null;
        for (User ban : bans) {
            server.banUser(ban);
        }
    }

    private void banByID(MessageCreateEvent event) {
    }
}
