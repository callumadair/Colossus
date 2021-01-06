package com.github.callumadair.colossus.management;

import com.github.callumadair.colossus.Bot.*;
import org.javacord.api.entity.server.*;
import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.util.*;

/** The type Ban. */
public class Ban extends BotAction {
  private final int MESSAGE_DELETE_DEFAULT = 30;

  /**
   * Instantiates a new Ban object.
   *
   * @param bot the specified bot
   */
  protected Ban(Bot bot) {
    super(bot);
  }

  @Override
  public void start() {
    getBot()
        .getApi()
        .addMessageCreateListener(
            event -> {
              if (bot.isBotModerator(
                  event.getMessageAuthor().asUser().get(), event.getServer().get())) {
                if (event
                    .getMessageContent()
                    .split(" ")[0]
                    .equalsIgnoreCase(getBot().getPrefix() + "ban")) {
                  banByMention(event);
                  banByMention(
                      event,
                      event.getMessageContent()
                          .split(" ")[event.getMessageContent().split(" ").length - 1]);
                  banByID(event);
                  banByID(
                      event,
                      event.getMessageContent()
                          .split(" ")[event.getMessageContent().split(" ").length - 1]);
                }
              }
            });
  }

  /**
   * Bans the mentioned user(s) from the server of the specified event.
   *
   * @param event the specified event.
   */
  private void banByMention(MessageCreateEvent event) {
    List<User> bans = event.getMessage().getMentionedUsers();
    Server server = event.getServer().get();
    for (User ban : bans) {
      server.banUser(ban);
    }
  }

  /**
   * Bans the mentioned user(s) from the server of the specified event with a specified reason.
   *
   * @param event the specified event.
   * @param reason the reason given for the ban.
   */
  private void banByMention(MessageCreateEvent event, String reason) {
    List<User> bans = event.getMessage().getMentionedUsers();
    Server server = event.getServer().get();
    for (User ban : bans) {
      server.banUser(ban, MESSAGE_DELETE_DEFAULT, reason);
    }
  }

  /**
   * Bans a selected user by their discord ID in the specified event server.
   *
   * @param event the specified event.
   */
  private void banByID(MessageCreateEvent event) {
    try {
      long banID = Long.parseLong(event.getMessageContent().split(" ")[1]);
      User ban = getBot().getApi().getUserById(banID).get();
      event.getServer().get().banUser(ban);
      event.getChannel().sendMessage(banID + " was banned from the server.");
    } catch (Exception e) {
      event.getChannel().sendMessage(e.getMessage());
    }
  }

  /**
   * Bans a selected user by their discord ID in the specified event server, with a specified
   * reason.
   *
   * @param event the specified event.
   * @param reason the specified reason
   */
  private void banByID(MessageCreateEvent event, String reason) {
    try {
      long banID = Long.parseLong(event.getMessageContent().split(" ")[1]);
      User ban = getBot().getApi().getUserById(banID).get();
      event.getServer().get().banUser(ban, MESSAGE_DELETE_DEFAULT, reason);
      event.getChannel().sendMessage(banID + " was banned from the server.");
    } catch (Exception e) {
      event.getChannel().sendMessage(e.getMessage());
    }
  }
}
