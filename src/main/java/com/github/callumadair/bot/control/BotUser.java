package com.github.callumadair.bot.control;

import org.javacord.api.entity.user.*;

public class BotUser {
  private User user;
}

enum BotPermission {
  ADMIN,
  MODERATOR,
  STANDARD,
  NON_USER;
}
enum ServerPermission {
  ADMIN,
  MODERATOR,
  STANDARD,
  NON_USER;
}

