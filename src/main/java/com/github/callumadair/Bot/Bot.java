package com.github.callumadair.Bot;

import com.github.callumadair.*;
import com.github.callumadair.data.*;
import com.github.callumadair.management.*;
import org.javacord.api.*;
import org.javacord.api.entity.permission.*;
import org.javacord.api.entity.server.*;
import org.javacord.api.entity.user.*;

import java.awt.*;
import java.util.*;

/**
 * A bot implements a discord bot and gives it features to enable it's functionality.
 *
 * @author Callum Adair
 * @version 0.3
 */
public class Bot {
  private Color roleColour;
  private DiscordApi api;
  private String prefix;
  private Role moderatorRole;

  /**
   * Specifies the api, command prefix and role colour.
   *
   * @param api the api for the bot to use.
   * @param roleColour the colour of the bot in the discord server to be set on join.
   */
  public Bot(DiscordApi api, Color roleColour) {
    setApi(api);
    setColour(roleColour);
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
   * Gets api.
   *
   * @return returns the current api.
   */
  public DiscordApi getApi() {
    return api;
  }

  /**
   * Sets prefix.
   *
   * @param prefix the prefix
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * Gets prefix.
   *
   * @return the prefix
   */
  public String getPrefix() {
    if (prefix != null) {
      return this.prefix;
    }
    return Values.DEFAULT_PREFIX.value;
  }

  /**
   * Sets the colour of the bot's role to the one specified.
   *
   * @param roleColour the specified colour.
   */
  private void setColour(Color roleColour) {
    this.roleColour = roleColour;
  }

  /**
   * Gets role colour.
   *
   * @return returns the colour of the bot's colour role.
   */
  public Color getRoleColour() {
    return roleColour;
  }

  /** Instantiates all classes and methods of the bot's functionality. */
  public void listener() {
    ArrayList<BotAction> botActions = new ArrayList<>();

    botActions.add(new BotSetup(this));
    botActions.add(new Management(this));
    botActions.add(new Data(this));
    botActions.add(new FungWah(this));
    botActions.add(new ShutDown(this));

    for (BotAction botAction : botActions) {
      botAction.start();
    }
  }

  /**
   * Sets moderator role.
   *
   * @param moderatorRole the moderator role
   */
  public void setModeratorRole(Role moderatorRole) {
    this.moderatorRole = moderatorRole;
  }

  /**
   * Gets moderator role.
   *
   * @return the moderator role
   */
  public Role getModeratorRole() {
    return moderatorRole;
  }

  /**
   * Is bot moderator boolean.
   *
   * @param user the user
   * @param server the server
   * @return the boolean
   */
  public boolean isBotModerator(User user, Server server) {
    return user.getRoles(server).contains(getModeratorRole());
  }
}

/** The enum Values. */
enum Values {
  /** Default prefix values. */
  DEFAULT_PREFIX("!");
  /** The Value. */
  String value;

  Values(String value) {
    this.value = value;
  }
}
