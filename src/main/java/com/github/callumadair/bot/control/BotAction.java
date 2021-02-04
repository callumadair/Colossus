package com.github.callumadair.bot.control;

/** The type BotAction, an abstract superclass for all bot actions. */
public abstract class BotAction {
  protected Bot bot;

  /**
   * Instantiates a new BotAction.
   *
   * @param bot the specified bot
   */
  protected BotAction(Bot bot) {
    setBot(bot);
  }

  /** Listener method for commands. */
  public abstract void action();

  /**
   * Sets the bot to the one specified.
   *
   * @param bot the bot
   */
  public void setBot(Bot bot) {
    this.bot = bot;
  }

  /**
   * Gets the bot.
   *
   * @return the bot
   */
  public Bot getBot() {
    return bot;
  }
}
