package com.github.callumadair;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

/**
 * A class for managing roles in a discord server.
 * 
 * @author Callum Adair
 * @version 0.1
 */
public class RoleManagement {
    private static List<Role> moderatorRoles;
    private static Role muted, member, everyone;
    private DiscordApi api;
    private Bot bot;

    /**
     * Creates a new instance of the class with the specified api and bot.
     * 
     * @param api the specified api.
     * @param bot the specified bot.
     */
    public RoleManagement(DiscordApi api, Bot bot) {
        setApi(api);
        setBot(bot);

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
     * Changes the bot to the one specified.
     * 
     * @param bot the specified bot.
     */
    private void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * 
     * @return returns the current bot.
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Implements the methods of the class.
     */
    public void listener() {
        api.addMessageCreateListener(event -> {
            addRole(event);
            autoRole(event);
            removeRole(event);
            clearRoles(event);
            createAdminRole(event);
            setModRole(event);
            createMutedRole(event);
            muteUser(event);
            roles(event);
        });
    }

    /**
     * Gives the mentioned user in the event the mentioned roles from the event.
     * 
     * @param event the specified event
     */
    private void addRole(MessageCreateEvent event) {

        if (event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "addrole")
                && event.getMessageAuthor().canManageRolesOnServer()) {
            List<User> newUsers = event.getMessage().getMentionedUsers();
            List<Role> newRoles = event.getMessage().getMentionedRoles();

            for (User user : newUsers) {
                EmbedBuilder roleMessage = new EmbedBuilder();
                roleMessage.setTitle("Role addition notification").setColor(Color.BLUE);

                StringBuilder roles = new StringBuilder();
                for (Role role : newRoles) {
                    user.addRole(role);
                    roles.append(role.getMentionTag() + "\n");
                }

                roleMessage.setDescription(user.getMentionTag() + " now has the following roles:\n" + roles.toString());
                event.getChannel().sendMessage(roleMessage);
            }
        }
    }

    /**
     * Sets the mentioned roles from the event to every new join in the discord
     * server.
     * 
     * @param event the specified event.
     */
    private void autoRole(MessageCreateEvent event) {
        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "autorole")) {
            List<Role> autoRoles = event.getMessage().getMentionedRoles();
            EmbedBuilder autoRoleMessage = new EmbedBuilder();
            autoRoleMessage.setTitle("Auto role notification").setColor(Color.CYAN);

            StringBuilder autoRoleAlert = new StringBuilder();
            autoRoleAlert.append("The following roles are now added on join:\n");
            for (Role role : autoRoles) {
                autoRoleAlert.append(role.getMentionTag() + "\n");
            }
            autoRoleMessage.setDescription(autoRoleAlert.toString());
            event.getChannel().sendMessage(autoRoleMessage);

            autoRole(autoRoles);
        }
    }

    /**
     * Sets the specified roles to be given to every new join in the discord server.
     * 
     * @param autoRoles the specified list of roles.
     */
    private void autoRole(List<Role> autoRoles) {
        api.addServerMemberJoinListener(listen -> {

            for (Role role : autoRoles) {
                listen.getUser().addRole(role);
            }
        });
    }

    /**
     * Removes the mention roles from the mentioned users in the specified event.
     * 
     * @param event the specified event.
     */
    private void removeRole(MessageCreateEvent event) {
        if (event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "removerole")
                && event.getMessageAuthor().canManageRolesOnServer()) {
            List<User> oldUsers = event.getMessage().getMentionedUsers();
            List<Role> oldRoles = event.getMessage().getMentionedRoles();

            for (User user : oldUsers) {
                EmbedBuilder roleRemoveMessage = new EmbedBuilder();
                roleRemoveMessage.setTitle("Role removal message").setColor(Color.RED);

                StringBuilder roles = new StringBuilder();
                for (Role role : oldRoles) {
                    user.removeRole(role);
                    roles.append(role.getMentionTag() + "\n");
                }

                roleRemoveMessage.setDescription(
                        user.getMentionTag() + " no longer has the following roles:\n" + roles.toString());
                event.getChannel().sendMessage(roleRemoveMessage);
            }
        }
    }

    /**
     * Deletes all the roles beneath the application is able to from the event
     * discord server.
     * 
     * @param event the specified event.
     */
    public void clearRoles(MessageCreateEvent event) {
        if (event.getMessageAuthor().isBotOwner()
                && event.getMessageContent().equalsIgnoreCase(bot.getPrefix() + "clearroles")) {
            List<Role> allRoles = event.getServer().get().getRoles();

            for (Role role : allRoles) {
                if (!role.isEveryoneRole()) {
                    event.getChannel().sendMessage(role.getMentionTag() + " has been deleted.");
                    role.delete();
                }

            }
        }
    }

    /**
     * Creates a new administrator role in the specified event server with the
     * specified role name and colour.
     * 
     * @param event the specified event.
     */
    private void createAdminRole(MessageCreateEvent event) {

        if ((event.getMessageAuthor().isServerAdmin())
                && (event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "createadminrole"))) {

            RoleBuilder newRole = event.getServer().get().createRoleBuilder();
            newRole.setName("Admin").setColor(Color.GREEN);

            PermissionsBuilder adminPerms = new PermissionsBuilder();
            adminPerms.setAllowed(PermissionType.ADMINISTRATOR);

            Permissions perms = adminPerms.build();
            newRole.setPermissions(perms);

            Role admin = newRole.create().join();
            admin.updateMentionableFlag(true).join();

            event.getChannel().sendMessage(admin.getMentionTag() + " is now an administrator role");

        }

    }

    /**
     * Gives the roles mentioned "moderator" permissions in the specified event's
     * discord server.
     * 
     * @param event the specified event.
     */
    private void setModRole(MessageCreateEvent event) {

        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "setmod")) {
            PermissionsBuilder modPerms = new PermissionsBuilder();
            modPerms.setAllowed(PermissionType.BAN_MEMBERS, PermissionType.DEAFEN_MEMBERS, PermissionType.KICK_MEMBERS,
                    PermissionType.MANAGE_ROLES, PermissionType.MUTE_MEMBERS, PermissionType.VIEW_AUDIT_LOG);
            Permissions perms = modPerms.build();

            List<Role> modRoles = event.getMessage().getMentionedRoles();
            for (Role modRole : modRoles) {
                modRole.updatePermissions(perms);
                moderatorRoles.add(modRole);
                event.getChannel().sendMessage(modRole.getName() + " is now a moderator role");
            }
        }

    }

    /**
     * Checks if the specified role is a "moderator" role.
     * 
     * @param role the specified role.
     * @return returns true if the role is a moderator role.
     */
    public boolean isMod(Role role) {
        if (moderatorRoles.contains(role)) {
            return true;
        } else
            return false;
    }

    /**
     * Creates a muted role, an ordinary member role and updates the permissions of
     * the everyone role in the specified event's discord server.
     * 
     * @param event the specified event.
     */
    private void createMutedRole(MessageCreateEvent event) {

        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "createmutedrole")) {

            PermissionsBuilder mutedPerms = new PermissionsBuilder();
            mutedPerms.setDenied(PermissionType.SEND_MESSAGES, PermissionType.SPEAK, PermissionType.STREAM);
            mutedPerms.setAllowed(PermissionType.READ_MESSAGES, PermissionType.READ_MESSAGE_HISTORY);
            Permissions perms = mutedPerms.build();

            RoleBuilder newMute = new RoleBuilder(event.getServer().get());
            newMute.setName("Muted").setPermissions(perms);
            muted = newMute.create().join();
            muted.updateMentionableFlag(true);

            PermissionsBuilder normalPerms = new PermissionsBuilder();
            normalPerms.setAllowed(PermissionType.SEND_MESSAGES, PermissionType.SPEAK, PermissionType.STREAM,
                    PermissionType.READ_MESSAGES, PermissionType.READ_MESSAGE_HISTORY);
            Permissions normPerms = normalPerms.build();
            RoleBuilder normalUser = new RoleBuilder(event.getServer().get());
            normalUser.setName("Member").setPermissions(normPerms);
            member = normalUser.create().join();
            member.updateMentionableFlag(false);

            PermissionsBuilder everyonePerms = new PermissionsBuilder();
            everyonePerms.setDenied(PermissionType.SEND_MESSAGES, PermissionType.SPEAK, PermissionType.STREAM);
            Permissions everyPerms = everyonePerms.build();
            everyone = event.getServer().get().getEveryoneRole();
            everyone.updatePermissions(everyPerms);
            everyone.updateMentionableFlag(false);

            Collection<User> allMembers = everyone.getUsers();
            for (User user : allMembers) {
                user.addRole(member);
            }

            List<Role> autoRoles = new ArrayList<Role>();
            autoRoles.add(member);
            autoRole(autoRoles);

            event.getChannel().sendMessage("`" + muted.getName() + "` is now a muted role");
        }

    }

    /**
     * Mute's the mentioned user in the specified event's discord server.
     * 
     * @param event the specified event.
     */
    private void muteUser(MessageCreateEvent event) {
        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "mute")) {
            List<User> newMutes = event.getMessage().getMentionedUsers();
            for (User user : newMutes) {
                user.addRole(muted);
                user.removeRole(member);

                EmbedBuilder muteMessage = new EmbedBuilder();
                muteMessage.setColor(Color.RED).setFooter("Muted by " + event.getMessageAuthor().getName())
                        .addField("Mute Notification", user.getMentionTag() + " has been muted.");
                event.getChannel().sendMessage(muteMessage);
            }
        }
        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(bot.getPrefix() + "unmute")) {
            List<User> newUnmutes = event.getMessage().getMentionedUsers();
            for (User user : newUnmutes) {
                user.addRole(member);
                user.removeRole(muted);

                EmbedBuilder unmuteMessage = new EmbedBuilder();
                unmuteMessage.setColor(Color.BLUE).setFooter("Unmuted by " + event.getMessageAuthor().getName())
                        .addField("Unmute Notification", user.getMentionTag() + "has been unmuted.");
                event.getChannel().sendMessage(unmuteMessage);
            }
        }
    }

    /**
     * Lists out all the roles in the specified event's discord server.
     * 
     * @param event the specified event.
     */
    private void roles(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(bot.getPrefix() + "roles")) {
            List<Role> allRoles = event.getServer().get().getRoles();
            StringBuilder roleMentions = new StringBuilder();
            for (Role role : allRoles) {
                roleMentions.append(role.getMentionTag() + "\n");
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(bot.getRoleColour()).setTitle("Roles in this server:")
                    .setDescription(roleMentions.toString());
            event.getChannel().sendMessage(embed);
        }
    }
}