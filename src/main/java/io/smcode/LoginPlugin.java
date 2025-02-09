package io.smcode;

import io.smcode.commands.LoginCommand;
import io.smcode.commands.RegisterCommand;
import io.smcode.listeners.UnauthorizedListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LoginPlugin extends JavaPlugin {
    // TODO: remove effects, add cancelled events like block breaking

    @Override
    public void onEnable() {
        final LoginManager loginManager = new LoginManager(this);

        getServer().getPluginManager().registerEvents(new UnauthorizedListener(loginManager), this);
        getCommand("login").setExecutor(new LoginCommand(loginManager));
        getCommand("register").setExecutor(new RegisterCommand(loginManager));
    }
}
