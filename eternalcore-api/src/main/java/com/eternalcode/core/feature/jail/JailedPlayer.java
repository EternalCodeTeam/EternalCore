package com.eternalcode.core.feature.jail;

public class JailedPlayer {

        private final String playerName;
        private final String remainingTime;
        private final String detainedBy;

        public JailedPlayer(String playerName, String remainingTime, String detainedBy) {
            this.playerName = playerName;
            this.remainingTime = remainingTime;
            this.detainedBy = detainedBy;
        }

        public String getPlayerName() {
            return this.playerName;
        }

        public String getRemainingTime() {
            return this.remainingTime;
        }

        public String getDetainedBy() {
            return this.detainedBy;
        }


}
