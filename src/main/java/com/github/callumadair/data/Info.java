package com.github.callumadair.data;

import com.github.callumadair.Bot.*;
import org.javacord.api.entity.message.embed.*;
import org.javacord.api.entity.server.*;
import org.javacord.api.entity.user.*;
import org.javacord.api.event.message.*;

import java.time.temporal.*;
import java.util.*;

/**
 * Allows the collection of information on a variety of different applications.
 *
 * @author Callum Adair
 * @version 0.2
 */
public class Info extends BotAction {

  /**
   * Instantiates a new Info with the specified bot.
   *
   * @param bot the specified bot
   */
  public Info(Bot bot) {
    super(bot);
  }

  /** Implements the methods of the Info class. */
  public void start() {
    getBot()
        .getApi()
        .addMessageCreateListener(
            event -> {
              serverInfo(event);
              botInfo(event);
            });
  }

  /**
   * Gets basic information about the bot user account in the event server.
   *
   * @param event the specified event.
   */
  private void botInfo(MessageCreateEvent event) {
    if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "botinfo")) {
      EmbedBuilder botInfo = new EmbedBuilder();
      User colossus = getBot().getApi().getYourself();

      botInfo
          .setTitle("Bot Info")
          .setColor(getBot().getRoleColour())
          .setDescription(getBot().getApi().getYourself().getName())
          .addField(
              "Servers",
              ":robot: "
                  + colossus.getName()
                  + " is present in "
                  + (colossus.getMutualServers().size())
                  + " servers");

      event.getChannel().sendMessage(botInfo);
    }
  }

  /**
   * Gets basic information about the specified event server.
   *
   * @param event the specified event
   */
  private void serverInfo(MessageCreateEvent event) {
    if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "serverinfo")) {
      EmbedBuilder serverInfo = new EmbedBuilder();
      Server server = event.getServer().orElse(null);

      try {
        assert server != null;
        serverInfo
            .setTitle("Server Info")
            .setColor(getBot().getRoleColour())
            .setDescription(server.getName())
            .addField(
                "Members",
                ":two_men_holding_hands: "
                    + server.getMemberCount()
                    + " members (:robot: "
                    + getBotCount(server)
                    + " bots), "
                    + getOnlineUsers(server)
                    + " online.")
            .addField(
                "Channels",
                (server.getChannels().size() - server.getChannelCategories().size()) + " channels")
            .addField("Owner", "<@" + server.getOwnerId() + ">")
            .addField(
                "Creation Date",
                server.getCreationTimestamp().truncatedTo(ChronoUnit.MINUTES).toString());
      } catch (NoSuchElementException e) {
        System.out.println(e.getMessage());
      }
      event.getChannel().sendMessage(serverInfo);
    }
  }

  /**
   * Calculates and returns the number of bot users that are members of the specified server.
   *
   * @param server the specified server.
   * @return returns the number of bots in the server.
   */
  private int getBotCount(Server server) {
    int botCount = 0;
    Collection<User> serverMembers = server.getMembers();
    for (User user : serverMembers) {
      if (user.isBot()) {
        ++botCount;
      }
    }
    return botCount;
  }

  /**
   * Calculates and returns the number of members of the specified server that are online.
   *
   * @param server the specified server.
   * @return returns the number of online members.
   */
  private int getOnlineUsers(Server server) {
    int onlineCount = 0;
    Collection<User> serverMembers = server.getMembers();
    for (User user : serverMembers) {
      if (user.getStatus().equals(UserStatus.ONLINE)) {
        ++onlineCount;
      }
    }
    return onlineCount;
  }
}
