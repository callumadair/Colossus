package com.github.callumadair;

import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.util.*;

public class ServerMemberManagement extends BotAction {

    /**
     * Instantiates a new ServerManagement object.
     *
     * @param bot the specified bot
     */
    ServerMemberManagement(Bot bot) {
        super(bot);
    }

    @Override
    public void listener() {
        getBot().getApi().addMessageCreateListener(event -> {
            kickSpecifiedMembers(event);
            banSpecifiedMembers(event);
        });
    }

    private void kickSpecifiedMembers(MessageCreateEvent event) {
        if (event.getMessageAuthor().canKickUsersFromServer()) {
            List<User> kickUsers = event.getMessage().getMentionedUsers();
            StringBuilder kickMessage = new StringBuilder();
            int count = 0;
            for (User user : kickUsers) {
                Objects.requireNonNull(event.getServer().orElse(null)).kickUser(user);
                kickMessage.append(user.getMentionTag()).append(" ");
                count++;
            }

            if (count == 1) {
                kickMessage.append("was kicked!");
            } else {
                kickMessage.append("were kicked!");
            }
            event.getChannel().sendMessage(kickMessage.toString());
        } else {
            event.getChannel().sendMessage("You cannot kick users from the server.");
        }
    }

    private void banSpecifiedMembers(MessageCreateEvent event) {

    }
}
