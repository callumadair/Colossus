package com.github.callumadair;

import org.javacord.api.*;

import java.awt.*;
import java.util.*;

/**
 * A bot implements a discord bot and gives it features to enable it's
 * functionality.
 *
 * @author Callum Adair
 * @version 0.2
 */
class Bot {
    private String commandPrefix;
    private Color roleColour;
    private DiscordApi api;

    /**
     * Specifies the api, command prefix and role colour.
     *
     * @param api           the api for the bot to use.
     * @param commandPrefix the prefix for the bot to use.
     * @param roleColour    the colour of the bot in the discord server to be set on
     *                      join.
     */
    public Bot(DiscordApi api, String commandPrefix, Color roleColour) {
        setApi(api);
        setPrefix(commandPrefix);
        setColour(roleColour);

    }

    /**
     * Changes the api to the one specified.
     *
     * @param api the specified api.
     */
    private void setApi(DiscordApi api) {
        this.api = api;
    }

    /**
     * @return returns the current api.
     */
    public DiscordApi getApi() {
        return api;
    }

    /**
     * Changes the command prefix to the specified string.
     *
     * @param commandPrefix the specified string.
     */
    private void setPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    /**
     * @return returns the current command prefix.
     */
    public String getPrefix() {
        return commandPrefix;
    }

    /**
     * Sets the colour of the bot's role to the one specified.
     *
     * @param roleColour the specified colour.
     */
    private void setColour(Color roleColour) {
        this.roleColour = roleColour;
    }

    /**
     * @return returns the colour of the bot's colour role.
     */
    public Color getRoleColour() {
        return roleColour;
    }

    /**
     * Instantiates all classes and methods of the bot's functionality.
     */
    public void listener() {
        ArrayList<BotAction> botActions = new ArrayList<>();

        botActions.add(new BotSetup(this));
        botActions.add(new FungWah(this));
        botActions.add(new Info(this));
        botActions.add(new Invites(this));
        botActions.add(new Pong(this));
        botActions.add(new Purge(this));
        botActions.add(new RoleManagement(this));
        botActions.add(new ShutDown(this));
        botActions.add(new Time(this));

        for (BotAction botAction : botActions) {
            botAction.listener();
        }
    }

}