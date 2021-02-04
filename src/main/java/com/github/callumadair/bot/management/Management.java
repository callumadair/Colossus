package com.github.callumadair.bot.management;

import com.github.callumadair.bot.control.*;

import java.util.*;

/** The type Management. */
public class Management extends BotAction {

  /**
   * Instantiates a new ServerManagement object.
   *
   * @param bot the specified bot
   */
  public Management(Bot bot) {
    super(bot);
  }

  @Override
  public void action() {
    ArrayList<BotAction> managementActions = new ArrayList<>();
    managementActions.add(new Kick(getBot()));
    managementActions.add(new Ban(getBot()));
    managementActions.add(new Purge(getBot()));
    managementActions.add(new RoleManagement(getBot()));

    for (BotAction action : managementActions) {
      action.action();
    }
  }
}
