package com.github.callumadair;

/**
 * The type Shut down.
 */
class ShutDown extends BotAction {

    /**
     * Instantiates a new Shut down.
     *
     * @param bot the bot
     */
    ShutDown(Bot bot) {
        super(bot);
    }

    @Override
    /*
     * Implements the shut down command for the bot.
     */
    public void listener() {
        getBot().getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equals(getBot().getPrefix() + "shutdown")
                    && event.getMessageAuthor().isBotOwner()) {
                event.getChannel().sendMessage("Owner confirmed. Bot shutting down!");
                System.exit(0);
            }
        });
    }
}
