package com.github.callumadair.management;

import com.github.callumadair.Bot.*;
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
                    event.getServer().orElse(null))) {
                kickByMention(event);
                kickByID(event);
            }
        });
    }

    private void kickByMention(MessageCreateEvent event) {
    }

    private void kickByID(MessageCreateEvent event) {
    }


}
