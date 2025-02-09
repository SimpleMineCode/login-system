package io.smcode;

import io.smcode.exceptions.NotRegisteredException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class LoginManager {
    private static final MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("sha256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<UUID, String> loggedIn = new HashMap<>();
    private final File passwordFile;
    private final YamlConfiguration config;

    public LoginManager(JavaPlugin plugin) {
        this.passwordFile = new File(plugin.getDataFolder(), "passwords.yml");
        this.config = YamlConfiguration.loadConfiguration(passwordFile);
        this.saveConfig();
    }

    public boolean isRegistered(Player player) {
        return this.config.isSet(player.getUniqueId().toString());
    }

    public boolean isLoggedIn(Player player) {
        return this.loggedIn.containsKey(player.getUniqueId())
                && this.loggedIn.get(player.getUniqueId()).equals(player.getAddress().getHostName());
    }

    public void register(Player player, String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            player.sendMessage("§cPasswords must match!");
            return;
        }

        if (isRegistered(player)) {
            player.sendMessage("§cYou are already registered. Use §e/login §cinstead.");
            return;
        }

        this.config.set(player.getUniqueId().toString(), hashPassword(password));
        player.sendMessage("§7Successfully registered. You can login with §a/login §7now.");
        saveConfig();
    }

    public void login(Player player, String password) throws NotRegisteredException {
        final String passwordHash = loadPassword(player);
        final String hostName = player.getAddress().getAddress().getHostName();

        if (!hashPassword(password).equals(passwordHash)) {
            player.sendMessage("§cWrong password");
            return;
        }

        this.loggedIn.put(player.getUniqueId(), hostName);
        player.sendMessage("§aYou are logged in.");
    }

    private String loadPassword(Player player) throws NotRegisteredException {
        final String path = player.getUniqueId().toString();

        if (!this.config.isSet(path))
            throw new NotRegisteredException(player);

        return this.config.getString(path);
    }

    private static String hashPassword(String password) {
        return bytesToHex(messageDigest.digest(password.getBytes()));
    }

    private static String bytesToHex(byte[] hash) {
        final StringBuilder hexString = new StringBuilder(2 * hash.length);

        for (byte b : hash) {
            final String hex = Integer.toHexString(0xff & b);

            if (hex.length() == 1)
                hexString.append('0');

            hexString.append(hex);
        }
        return hexString.toString();
    }

    private void saveConfig() {
        try {
            this.config.save(passwordFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
