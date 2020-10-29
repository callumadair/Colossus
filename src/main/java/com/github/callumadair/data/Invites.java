package com.github.callumadair.data;

import java.util.*;

import com.github.callumadair.Bot.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.server.invite.Invite;
import org.javacord.api.entity.server.invite.InviteBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

/**
 * Implements invites for bots and servers.
 * 
 * @author Callum Adair
 * @version 0.5
 */
public class Invites extends BotAction{

    /**
     * Creates a new instance of the invite class with the specified bot.
     *
     * @param bot the specified bot.
     */
    public Invites(Bot bot) {
        super(bot);
    }

    /**
     * Implements the methods of the class.
     */
    public void listener() {
        getBot().getApi().addMessageCreateListener(event -> {
            createServerInvite(event);
            createBotInvite(event);
            sendServerInvite(event);
            sendBotInvite(event);
        });
    }

    /**
     * Creates a new invitation for the server in the specified event channel.
     * 
     * @param event the specified event.
     */
    private void createServerInvite(MessageCreateEvent event) {

        if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "serverinvite")) {
            event.getChannel().sendMessage(buildServerInvite(event));
        }

    }

    /**
     * Sends an invite to the event's discord server to all users mentioned in the
     * event message.
     * 
     * @param event the specified event.
     */
    private void sendServerInvite(MessageCreateEvent event) {
        if (event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "sendserverinvite")) {
            List<User> users = event.getMessage().getMentionedUsers();
            for (User user : users) {
                user.sendMessage(buildServerInvite(event));
            }
        }
    }

    /**
     * Creates an embed builder containing a server invite for the server the
     * specified event occurred in.
     * 
     * @param event the specified event.
     * @return returns an embed builder with a server invite.
     */
    private EmbedBuilder buildServerInvite(MessageCreateEvent event) {
        InviteBuilder inviteBuilder = new InviteBuilder(event.getChannel().asServerChannel().orElse(null));
        Invite invite = inviteBuilder.create().join();

        EmbedBuilder serverInvite = new EmbedBuilder();
        serverInvite.setTitle("Invite to " + Objects.requireNonNull(event.getServer().orElse(null)).getName())
                .setFooter("Made by Cal").setUrl(invite.getUrl().toString()).setTimestampToNow()
                .setAuthor(event.getMessageAuthor()).setColor(getBot().getRoleColour());
        return serverInvite;
    }

    /**
     * Creates a new invite for the bot application in the specified event channel.
     * 
     * @param event the specified event.
     */
    private void createBotInvite(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "botinvite")) {
            event.getChannel().sendMessage(buildBotInvite(event));
        }
    }

    /**
     * Sends an invite for the bot user to all users mentioned in the specified
     * event message.
     * 
     * @param event the specified event.
     */
    private void sendBotInvite(MessageCreateEvent event) {
        if (event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "sendbotinvite")) {
            List<User> users = event.getMessage().getMentionedUsers();
            for (User user : users) {
                user.sendMessage(buildBotInvite(event));
            }
        }
    }

    /**
     * Creates an embed builder containing an invite for the bot user.
     * 
     * @param event the event calling the creation of the embed builder.
     * @return returns an embed builder containing a bot invite.
     */
    private EmbedBuilder buildBotInvite(MessageCreateEvent event) {
        PermissionsBuilder colossusPermsBuilder = new PermissionsBuilder();
        colossusPermsBuilder.setAllowed(PermissionType.ADMINISTRATOR);
        Permissions colossusPerms = colossusPermsBuilder.build();

        EmbedBuilder botInvite = new EmbedBuilder();
        botInvite.setTitle("Invite Colossus here!").setUrl(getBot().getApi().createBotInvite(colossusPerms))
                .setAuthor(event.getMessageAuthor()).setFooter("Made by Cal").setTimestampToNow()
                .setColor(getBot().getRoleColour());
        return botInvite;
    }

}
