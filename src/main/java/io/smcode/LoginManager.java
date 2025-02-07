package io.smcode;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class LoginManager {
    private final Map<UUID, String> loggedIn = new HashMap<>();

    public boolean isLoggedIn(Player player) {
        return this.loggedIn.containsKey(player.getUniqueId())
                && this.loggedIn.get(player.getUniqueId()).equals(player.getAddress().getHostName());
    }

    public void login(Player player, String password) {
        final String passwordHash = loadPassword(player);
        final String hostName = player.getAddress().getAddress().getHostName();

        System.out.println(hostName);

        this.loggedIn.put(player.getUniqueId(), hostName);
    }

    private String loadPassword(Player player) {
        return "password"; // TODO: Change
    }
}
