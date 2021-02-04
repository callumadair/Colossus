package com.github.callumadair.bot.data;

import com.github.callumadair.bot.control.*;

import java.util.*;

/** The type Data. */
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
  public void action() {
    ArrayList<BotAction> dataActions = new ArrayList<>();

    dataActions.add(new Info(getBot()));
    dataActions.add(new Invites(getBot()));
    dataActions.add(new Pong(getBot()));
    dataActions.add(new Time(getBot()));

    for (BotAction dataAction : dataActions) {
      dataAction.action();
    }
  }
}
