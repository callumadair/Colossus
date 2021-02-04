package com.github.callumadair.bot.data;

public class BotDatabase {
    private String url;

    public BotDatabase(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
