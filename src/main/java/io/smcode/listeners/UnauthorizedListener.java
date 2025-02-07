package io.smcode.listeners;

import io.smcode.LoginManager;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UnauthorizedListener implements Listener {
    private final LoginManager loginManager;

    public UnauthorizedListener(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (!this.loginManager.isLoggedIn(player)) {
            player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, -1, false, false, false));
            player.sendRichMessage("<red>Please login");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (this.loginManager.isLoggedIn(player))
            return;

        final Location from = event.getFrom();
        final Location to = event.getTo();

        event.setCancelled(from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ());
    }
}
