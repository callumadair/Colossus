package com.github.callumadair.management;

import com.github.callumadair.Bot.*;

import java.util.*;

public class ServerManagement extends BotAction {

    /**
     * Instantiates a new ServerManagement object.
     *
     * @param bot the specified bot
     */
    ServerManagement(Bot bot) {
        super(bot);
    }

    @Override
    public void listener() {
        ArrayList<BotAction> managementActions = new ArrayList<>();
        managementActions.add(new Kick(getBot()));
        managementActions.add(new Ban(getBot()));
        managementActions.add(new Purge(getBot()));
        managementActions.add(new RoleManagement(getBot()));
    }
}
