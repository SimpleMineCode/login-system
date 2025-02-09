package io.smcode.exceptions;

import org.bukkit.entity.Player;

public class NotRegisteredException extends Exception {
    private final Player player;

    public NotRegisteredException(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
