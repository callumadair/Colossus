package com.github.callumadair.data;

import com.github.callumadair.Bot.*;

import java.util.*;

public class Data extends BotAction {
    /**
     * Instantiates a new Data object.
     *
     * @param bot the specified bot
     */
    public Data(Bot bot) {
        super(bot);
    }

    @Override
    public void start() {
        ArrayList<BotAction> dataActions = new ArrayList<>();

        dataActions.add(new Info(getBot()));
        dataActions.add(new Invites(getBot()));
        dataActions.add(new Pong(getBot()));
        dataActions.add(new Time(getBot()));
    }
}
