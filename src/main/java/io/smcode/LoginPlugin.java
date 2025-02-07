package io.smcode;

import io.smcode.listeners.UnauthorizedListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LoginPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        final LoginManager loginManager = new LoginManager();

        getServer().getPluginManager().registerEvents(new UnauthorizedListener(loginManager), this);
    }
}
