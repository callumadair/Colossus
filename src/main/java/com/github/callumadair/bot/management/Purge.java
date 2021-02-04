package com.github.callumadair.bot.management;

import com.github.callumadair.bot.control.*;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.*;

/**
 * A class for mass deletion of messages in a discord server.
 *
 * @author Callum Adair
 * @version 0.2
 */
public class Purge extends BotAction {

  /**
   * Creates a new instance of the class with the specified bot.
   *
   * @param bot the specified bot.
   */
  public Purge(Bot bot) {
    super(bot);
  }

  /** Implements the methods of the class. */
  public void action() {
    getBot()
        .getApi()
        .addMessageCreateListener(
            event -> {
              if (bot.isBotModerator(
                  event.getMessageAuthor().asUser().get(), event.getServer().get())) {
                if (event
                    .getMessageContent()
                    .split(" ")[0]
                    .equalsIgnoreCase(getBot().getPrefix() + "purge")) {
                  purgeMessages(event);
                }
              }
            });
  }

  /** Deletes the specified number of messages starting from the most recent. */
  private void purgeMessages(MessageCreateEvent event) {
    String[] messageArray = event.getMessageContent().split(" ");
    int numOfMessages = Integer.parseInt(messageArray[messageArray.length - 1]);

    MessageSet messages = event.getChannel().getMessages(numOfMessages).join();
    for (Message message : messages) {
      message.delete();
    }
  }
}
