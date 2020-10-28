package com.github.callumadair;

import org.javacord.api.*;
import org.javacord.api.entity.permission.*;

import java.awt.*;

/**
 * Runs the Bot.
 */
public class Main {
    public static void main(String[] args) {
        //String token = "";

        DiscordApi api = new DiscordApiBuilder().setToken(Token.TOKEN.token).login().join();

        PermissionsBuilder colossusPermsBuilder = new PermissionsBuilder();
        colossusPermsBuilder.setAllowed(PermissionType.ADMINISTRATOR);
        Permissions colossusPerms = colossusPermsBuilder.build();

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite(colossusPerms));
        Bot colossus = new Bot(api, Color.CYAN);
        colossus.listener();
    }

}
