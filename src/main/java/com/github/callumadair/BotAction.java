package com.github.callumadair;

/**
 * The type BotAction, an abstract superclass for all bot actions.
 */
public abstract class BotAction {
    private Bot bot;

    /**
     * Instantiates a new BotAction.
     *
     * @param bot the specified bot
     */
    BotAction(Bot bot) {
        setBot(bot);
    }

    /**
     * Listener method for commands.
     */
    public abstract void listener();

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
