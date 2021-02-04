package com.github.callumadair.bot.control;

/** The type Shut down. */
public class ShutDown extends BotAction {

  /**
   * Instantiates a new Shut down.
   *
   * @param bot the bot
   */
  public ShutDown(Bot bot) {
    super(bot);
  }

  @Override
  /*
   * Implements the shut down command for the bot.
   */
  public void action() {
    getBot()
        .getApi()
        .addMessageCreateListener(
            event -> {
              if (event.getMessageContent().equals(getBot().getPrefix() + "shutdown")
                  && event.getMessageAuthor().isBotOwner()) {
                event.getChannel().sendMessage("Owner confirmed. Bot shutting down!");
                System.exit(0);
              }
            });
  }
}
