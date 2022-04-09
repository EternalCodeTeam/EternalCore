package com.eternalcode.core.chat;

public interface ChatSettings {

    ChatSettings NONE = new ChatSettings() {

        private boolean chatEnabled = true;
        private double chatDelay = 2.0;

        @Override
        public boolean isChatEnabled() {
            return chatEnabled;
        }

        @Override
        public void setChatEnabled(boolean chatEnabled) {
            this.chatEnabled = chatEnabled;
        }

        @Override
        public double getChatDelay() {
            return chatDelay;
        }

        @Override
        public void setChatDelay(double chatDelay) {
            this.chatDelay = chatDelay;
        }

    };

    boolean isChatEnabled();

    void setChatEnabled(boolean chatEnabled);

    double getChatDelay();

    void setChatDelay(double chatDelay);

}
