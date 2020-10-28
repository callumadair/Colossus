package com.github.callumadair;

/**
 * The type BotAction, an abstact superclass for all bot actions.
 */
public abstract class BotAction {
    private Bot bot;

    /**
     * Instantiates a new Command.
     *
     * @param bot the specified bot
     */
    BotAction(Bot bot) {
        setBot(bot);
    }

    /**
     * Listener.
     */
    public abstract void listener();

    /**
     * Gets bot.
     *
     * @return the bot
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Sets bot.
     *
     * @param bot the bot
     */
    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
