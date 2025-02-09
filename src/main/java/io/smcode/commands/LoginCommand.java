package io.smcode.commands;

import io.smcode.LoginManager;
import io.smcode.exceptions.NotRegisteredException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoginCommand implements CommandExecutor {
    private final LoginManager loginManager;

    public LoginCommand(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§cUsage: /" + label + " <password>");
            return true;
        }

        final String password = args[0];

        try {
            this.loginManager.login(player, password);
        } catch (NotRegisteredException e) {
            player.sendMessage("§cYou need to register first with §e/register.");
        }

        return true;
    }
}
