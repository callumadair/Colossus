package com.github.callumadair;

import java.awt.Color;
import java.util.*;

import com.github.callumadair.Bot.*;
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
 * @version 0.2
 */
public class RoleManagement extends BotAction {
    private static Role muted;
    private static Role member;

    /**
     * Creates a new instance of the class with the specified bot.
     *
     * @param bot the specified bot.
     */
    public RoleManagement(Bot bot) {
        super(bot);
    }


    /**
     * Implements the methods of the class.
     */
    public void listener() {
        getBot().getApi().addMessageCreateListener(event -> {
            addRole(event);
            autoRole(event);
            removeRole(event);
            clearRoles(event);
            createAdminRole(event);
            setModeratorRoles(event);
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

        if (event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "addrole")
                && event.getMessageAuthor().canManageRolesOnServer()) {
            List<User> newUsers = event.getMessage().getMentionedUsers();
            List<Role> newRoles = event.getMessage().getMentionedRoles();

            for (User user : newUsers) {
                EmbedBuilder roleMessage = new EmbedBuilder();
                roleMessage.setTitle("Role addition notification").setColor(Color.BLUE);

                StringBuilder roles = new StringBuilder();
                for (Role role : newRoles) {
                    user.addRole(role);
                    roles.append(role.getMentionTag()).append("\n");
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
                && event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "autorole")) {
            List<Role> autoRoles = event.getMessage().getMentionedRoles();
            EmbedBuilder autoRoleMessage = new EmbedBuilder();
            autoRoleMessage.setTitle("Auto role notification").setColor(Color.CYAN);

            StringBuilder autoRoleAlert = new StringBuilder();
            autoRoleAlert.append("The following roles are now added on join:\n");
            for (Role role : autoRoles) {
                autoRoleAlert.append(role.getMentionTag()).append("\n");
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
        getBot().getApi().addServerMemberJoinListener(listen -> {

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
        if (event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "removerole")
                && event.getMessageAuthor().canManageRolesOnServer()) {
            List<User> oldUsers = event.getMessage().getMentionedUsers();
            List<Role> oldRoles = event.getMessage().getMentionedRoles();

            for (User user : oldUsers) {
                EmbedBuilder roleRemoveMessage = new EmbedBuilder();
                roleRemoveMessage.setTitle("Role removal message").setColor(Color.RED);

                StringBuilder roles = new StringBuilder();
                for (Role role : oldRoles) {
                    user.removeRole(role);
                    roles.append(role.getMentionTag()).append("\n");
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
                && event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "clearroles")) {
            List<Role> allRoles = Objects.requireNonNull(event.getServer().orElse(null)).getRoles();

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
                && (event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "createadminrole"))) {

            RoleBuilder newRole = Objects.requireNonNull(event.getServer().orElse(null)).createRoleBuilder();
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
    private void setModeratorRoles(MessageCreateEvent event) {

        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "setmod")) {
            PermissionsBuilder modPerms = new PermissionsBuilder();
            modPerms.setAllowed(PermissionType.BAN_MEMBERS, PermissionType.DEAFEN_MEMBERS, PermissionType.KICK_MEMBERS,
                    PermissionType.MANAGE_ROLES, PermissionType.MUTE_MEMBERS, PermissionType.VIEW_AUDIT_LOG);
            Permissions perms = modPerms.build();

            List<Role> moderatorRoles = event.getMessage().getMentionedRoles();
            for (Role modRole : moderatorRoles) {
                modRole.updatePermissions(perms);
                moderatorRoles.add(modRole);
                event.getChannel().sendMessage(modRole.getName() + " is now a moderator role");
            }
        }

    }

    /**
     * Creates a muted role, an ordinary member role and updates the permissions of
     * the everyone role in the specified event's discord server.
     *
     * @param event the specified event.
     */
    private void createMutedRole(MessageCreateEvent event) {

        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "createmutedrole")) {

            PermissionsBuilder mutedPerms = new PermissionsBuilder();
            mutedPerms.setDenied(PermissionType.SEND_MESSAGES, PermissionType.SPEAK, PermissionType.STREAM);
            mutedPerms.setAllowed(PermissionType.READ_MESSAGES, PermissionType.READ_MESSAGE_HISTORY);
            Permissions perms = mutedPerms.build();

            RoleBuilder newMute = new RoleBuilder(event.getServer().orElse(null));
            newMute.setName("Muted").setPermissions(perms);
            muted = newMute.create().join();
            muted.updateMentionableFlag(true);

            PermissionsBuilder normalPerms = new PermissionsBuilder();
            normalPerms.setAllowed(PermissionType.SEND_MESSAGES, PermissionType.SPEAK, PermissionType.STREAM,
                    PermissionType.READ_MESSAGES, PermissionType.READ_MESSAGE_HISTORY);
            Permissions normPerms = normalPerms.build();
            RoleBuilder normalUser = new RoleBuilder(event.getServer().orElse(null));
            normalUser.setName("Member").setPermissions(normPerms);
            member = normalUser.create().join();
            member.updateMentionableFlag(false);

            PermissionsBuilder everyonePerms = new PermissionsBuilder();
            everyonePerms.setDenied(PermissionType.SEND_MESSAGES, PermissionType.SPEAK, PermissionType.STREAM);
            Permissions everyPerms = everyonePerms.build();
            Role everyone = Objects.requireNonNull(event.getServer().orElse(null)).getEveryoneRole();
            everyone.updatePermissions(everyPerms);
            everyone.updateMentionableFlag(false);

            Collection<User> allMembers = everyone.getUsers();
            for (User user : allMembers) {
                user.addRole(member);
            }

            List<Role> autoRoles = new ArrayList<>();
            autoRoles.add(member);
            autoRole(autoRoles);

            event.getChannel().sendMessage("`" + muted.getName() + "` is now a muted role");
        }

    }

    /**
     * Mutes the mentioned user in the specified event's discord server.
     *
     * @param event the specified event.
     */
    private void muteUser(MessageCreateEvent event) {
        if (event.getMessageAuthor().isServerAdmin()
                && event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "mute")) {
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
                && event.getMessageContent().toLowerCase().contains(getBot().getPrefix() + "unmute")) {
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
        if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "roles")) {
            List<Role> allRoles = Objects.requireNonNull(event.getServer().orElse(null)).getRoles();
            StringBuilder roleMentions = new StringBuilder();
            for (Role role : allRoles) {
                roleMentions.append(role.getMentionTag()).append("\n");
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(getBot().getRoleColour()).setTitle("Roles in this server:")
                    .setDescription(roleMentions.toString());
            event.getChannel().sendMessage(embed);
        }
    }
}