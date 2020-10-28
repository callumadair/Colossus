package com.github.callumadair;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.user.User;

/**
 * Implements the configuration of a bot on using a specified api.
 *
 * @author Callum Adair
 * @version 0.4
 */
public class BotSetup extends BotAction {

    /**
     * Creates a new instance of the setup.
     *
     * @param bot the specified bot.
     */
    public BotSetup(Bot bot) {
        super(bot);
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
        getBot().getApi().addServerJoinListener(listener -> {

            User colossus = getBot().getApi().getYourself();

            RoleBuilder colourBuilder = new RoleBuilder(listener.getServer());
            colourBuilder.setColor(getBot().getRoleColour()).setName("Bot Colour");
            Role colour = colourBuilder.create().join();
            colour.addUser(colossus);

        });
    }
}