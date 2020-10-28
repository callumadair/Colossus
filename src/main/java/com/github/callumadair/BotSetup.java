package com.github.callumadair;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.user.User;

/**
 * Implements the configuration of a bot on using a specified api.
 *
 * @author Callum Adair
 * @version 0.2
 */
public class BotSetup {
    private Bot bot;

    /**
     * Creates a new instance of the setup.
     *
     * @param api the specified api.
     * @param bot the specified bot.
     */
    public BotSetup(Bot bot) {
        setBot(bot);
    }


    /**
     * Changes the bot to the one specified.
     *
     * @param bot the specified bot.
     */
    private void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * @return returns the current bot.
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Implements the methods of the setup.
     */
    public void listener() {
        roleSetup();
    }

    /**
     * Sets the bot's displayed role colour in the discord server.
     */
    private void roleSetup() {
        bot.getApi().addServerJoinListener(listener -> {

            User colossus = bot.getApi().getYourself();

            RoleBuilder colourBuilder = new RoleBuilder(listener.getServer());
            colourBuilder.setColor(bot.getRoleColour()).setName("Bot Colour");
            Role colour = colourBuilder.create().join();
            colour.addUser(colossus);

        });
    }
}