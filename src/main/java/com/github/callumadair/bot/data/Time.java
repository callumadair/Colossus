package com.github.callumadair.bot.data;

import com.github.callumadair.bot.control.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.time.*;
import java.time.temporal.*;

/**
 * Gets the current time on google.
 *
 * @author Callum Adair
 * @version 0.2
 */
public class Time extends BotAction {

  /**
   * Creates a new instance of the class with the specified bot.
   *
   * @param bot the specified bot.
   */
  public Time(Bot bot) {
    super(bot);
  }

  /** Implements the methods of the class. */
  public void action() {
    getTime();
  }

  /** Gets the current time in an embed. */
  private void getTime() {
    getBot()
        .getApi()
        .addMessageCreateListener(
            event -> {
              if (event.getMessageContent().equalsIgnoreCase(getBot().getPrefix() + "time")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed
                    .setTitle(
                        "Bot time is currently "
                            + LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toString())
                    .setColor(getBot().getRoleColour());
                event.getChannel().sendMessage(embed);
              }
            });
  }
}
