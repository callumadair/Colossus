package com.github.callumadair;

import org.javacord.api.*;

import java.awt.*;

/**
 * A bot implements a discord bot and gives it features to enable it's
 * functionality.
 * 
 * @author Callum Adair
 * @version 0.1
 */
public class Bot {
    private String commandPrefix;
    private Color roleColour;
    private DiscordApi api;

    /**
     * Specifies the api, command prefix and rolecolour.
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
     * 
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
     * 
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
     * 
     * @return returns the colour of the bot's colour role.
     */
    public Color getRoleColour() {
        return roleColour;
    }

    /**
     * Instantiates all classes and methods of the bot's functionality.
     */
    public void listener() {
        com.github.callumadair.Pong pong = new com.github.callumadair.Pong(this);
        pong.listener();

        Time time = new Time(this);
        time.listener();

        com.github.callumadair.Invites invite = new com.github.callumadair.Invites(this);
        invite.listener();

        Info info = new Info(this);
        info.listener();

        com.github.callumadair.Purge purge = new com.github.callumadair.Purge(this);
        purge.listener();

        com.github.callumadair.RoleManagement roleManager = new com.github.callumadair.RoleManagement(this);
        roleManager.listener();

        com.github.callumadair.BotSetup setup = new com.github.callumadair.BotSetup(this);
        setup.listener();

        com.github.callumadair.FungWah fung = new com.github.callumadair.FungWah(this);
        fung.listener();

    }

}