package com.eternalcode.core.feature.automessage;

import java.util.List;

public class AutoMessage {

    private final AutoMessageSettings settings;
    private final List<AutoMessageStack> stacks;

    private int lastMessageIndex;

    public AutoMessage(AutoMessageSettings settings, List<AutoMessageStack> stacks) {
        this.settings = settings;
        this.stacks = stacks;
    }

    public AutoMessageStack draw() {
        if (this.settings.drawMode() == AutoMessageSettings.DrawMode.RANDOM) {
            int randomStackIndex = (int) (Math.random() * this.stacks.size());

            return this.stacks.get(randomStackIndex);
        }

        if (this.lastMessageIndex >= this.stacks.size()) {
            this.lastMessageIndex = 0;
        }

        return this.stacks.get(this.lastMessageIndex++);
    }

    public AutoMessageStack extractStack(AutoMessageStack drawedStack, List<AutoMessageStack> stacks) {
        return stacks.stream().filter(stack -> stack.equals(drawedStack)).findFirst().orElse(stacks.get(0));
    }

}
