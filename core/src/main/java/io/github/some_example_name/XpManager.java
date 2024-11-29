package io.github.some_example_name;

import io.github.some_example_name.character.Player;

public class XpManager {
    private int currentXp;
    private int xpToNextLevel;
    private int level;
    private Player player;

    public XpManager(Player player) {
        this.level = 1;
        this.currentXp = 0;
        this.xpToNextLevel = 50;
        this.player = player;
    }

    public void addXp(int xp) {
        currentXp += xp;
        if (currentXp >= xpToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        currentXp -= xpToNextLevel;
        xpToNextLevel *= 2; // Example: double the XP required for the next level
        player.levelUp(); // Appelle la méthode levelUp de Player
    }

    public void reset() {
        this.level = 1;
        this.currentXp = 0;
        this.xpToNextLevel = 50;
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public int getLevel() {
        return level;
    }
}