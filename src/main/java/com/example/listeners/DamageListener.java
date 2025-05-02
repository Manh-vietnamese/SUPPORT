package com.example.listeners;

import com.example.SupportServer; 

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class DamageListener implements Listener {
    private final SupportServer plugin;

    // Thêm constructor này
    public DamageListener(SupportServer plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player) || event.isCancelled()) return;
        
        plugin.getCooldownManager().applyCooldown(player);
        plugin.getCooldownManager().startCooldownDisplay(player);
    }
    
    // Xóa tham số CommandSender không dùng
    @EventHandler(priority = EventPriority.HIGH) 
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("Sunflower.SP.admin")) return;
    
        String rawCommand = event.getMessage().substring(1).split(" ")[0].toLowerCase();
        
        if (plugin.getConfigManager().getBlockedCommands().contains(rawCommand) && 
            plugin.getCooldownManager().isInCooldown(player)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getMessager().get("no_command"));
        }
    }
}