package com.github.callumadair.MemberManagement;

import com.github.callumadair.Bot.*;

import java.util.*;

public class ServerMemberManagement extends BotAction {

    /**
     * Instantiates a new ServerManagement object.
     *
     * @param bot the specified bot
     */
    ServerMemberManagement(Bot bot) {
        super(bot);
    }

    @Override
    public void listener() {
        ArrayList<BotAction> memberManagementActions = new ArrayList<>();
        memberManagementActions.add(new Kick(getBot()));
        memberManagementActions.add(new Ban(getBot()));
    }
}
