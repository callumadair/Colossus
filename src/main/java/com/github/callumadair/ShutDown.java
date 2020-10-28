package com.github.callumadair;

class ShutDown extends BotAction {

    ShutDown(Bot bot) {
        super(bot);
    }

    @Override
    public void listener() {
        getBot().getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equals(getBot().getPrefix() + "shutdown")
                    && event.getMessageAuthor().isBotOwner()) {
                System.exit(0);
            }
        });
    }
}
