package com.github.callumadair.management;

import com.github.callumadair.Bot.*;
import org.javacord.api.event.message.*;

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
            kickByMention(event);
            kickByID(event);
        });
    }
    private void kickByMention(MessageCreateEvent event) {
    }
    private void kickByID(MessageCreateEvent event) {
    }


}
