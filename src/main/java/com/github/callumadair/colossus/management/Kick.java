package com.github.callumadair.colossus.management;

import com.github.callumadair.colossus.Bot.*;
import org.javacord.api.entity.server.*;
import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.util.*;

/** The type Kick. */
public class Kick extends BotAction {
  /**
   * Instantiates a new Kick action.
   *
   * @param bot the specified bot
   */
  protected Kick(Bot bot) {
    super(bot);
  }

  @Override
  public void start() {
    getBot()
        .getApi()
        .addMessageCreateListener(
            event -> {
              if (bot.isBotModerator(
                      event.getMessageAuthor().asUser().get(), event.getServer().orElse(null))
                  && event
                      .getMessageContent()
                      .split(" ")[0]
                      .equalsIgnoreCase(getBot().getPrefix() + "kick")) {
                kickByMention(event);
                kickByMention(
                    event,
                    event.getMessageContent()
                        .split(" ")[event.getMessageContent().split(" ").length - 1]);
                kickByID(event);
                kickByID(
                    event,
                    event.getMessageContent()
                        .split(" ")[event.getMessageContent().split(" ").length - 1]);
              }
            });
  }

  /**
   * Kicks the mentioned user(s) from the server of the specified event.
   *
   * @param event the specified event.
   */
  private void kickByMention(MessageCreateEvent event) {
    List<User> kicks = event.getMessage().getMentionedUsers();
    Server server = event.getServer().get();
    for (User kick : kicks) {
      server.kickUser(kick);
    }
  }

  /**
   * Kicks the mentioned user(s) from the server of the specified event, with a specific reason.
   *
   * @param event the specified event.
   * @param reason the reason given for kicking.
   */
  private void kickByMention(MessageCreateEvent event, String reason) {
    List<User> kicks = event.getMessage().getMentionedUsers();
    Server server = event.getServer().get();
    for (User kick : kicks) {
      server.kickUser(kick, reason);
    }
  }

  /**
   * Kicks the specified user by their discord ID
   *
   * @param event the specified event
   */
  private void kickByID(MessageCreateEvent event) {
    try {
      long kickID = Long.parseLong(event.getMessageContent().split(" ")[1]);
      User kick = getBot().getApi().getUserById(kickID).get();
      event.getServer().get().kickUser(kick);
      event.getChannel().sendMessage(kickID + " was kicked from the server.");
    } catch (Exception e) {
      event.getChannel().sendMessage(e.getMessage());
    }
  }

  /**
   * Kicks a specified user by their discord ID in the specified event server, with a specified
   * reason.
   *
   * @param event the specified event.
   * @param reason the specified reason
   */
  private void kickByID(MessageCreateEvent event, String reason) {
    try {
      long banID = Long.parseLong(event.getMessageContent().split(" ")[1]);
      User kick = getBot().getApi().getUserById(banID).get();
      event.getServer().get().kickUser(kick, reason);
      event.getChannel().sendMessage(banID + " was kicked from the server.");
    } catch (Exception e) {
      event.getChannel().sendMessage(e.getMessage());
    }
  }
}
