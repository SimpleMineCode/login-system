package io.smcode.commands;

import io.smcode.LoginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {
    private final LoginManager loginManager;

    public RegisterCommand(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("§cUsage: /" + label + " <password> <passwordConfirm>");
            return true;
        }

        this.loginManager.register(player, args[0], args[1]);

        return true;
    }
}
