package ComBat.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ComBat.Main_plugin;

public class CB_Cooldown {
    private final Main_plugin plugin;
    private final CB_ActionBar actionBarManager;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final Map<UUID, Integer> runningTasks = new HashMap<>();
    
    public CB_Cooldown(Main_plugin plugin) {
        this.plugin = plugin;
        this.actionBarManager = new CB_ActionBar(plugin.getProtocolManager());
    }
    
    public void applyCooldown(Player player) {
        int seconds = plugin.getConfigManager().getCooldownSeconds();
        cooldowns.put(
            player.getUniqueId(),
            System.currentTimeMillis() + (seconds * 1000L)
        );
    }
    
    public boolean isInCooldown(Player player) {
        return cooldowns.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis();
    }

    public long getRemainingTime(Player player) {
        long endTime = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        return Math.max(0, (endTime - System.currentTimeMillis()) / 1000);
    }

    public void startCooldownDisplay(Player player) {
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            long secondsLeft = getRemainingTime(player);
            if (secondsLeft <= 0) {
                stopCooldownDisplay(player);
                return;
            }
            
            String message = formatCooldownMessage(secondsLeft);
            actionBarManager.sendCustomActionBar(player, message);
        }, 0L, 20L);
        
        runningTasks.put(player.getUniqueId(), taskId);
    }
    
    private String formatCooldownMessage(long seconds) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("time", String.valueOf(seconds));
        return plugin.getMessager().get("cooldown-message", placeholders);
    }

    public void stopCooldownDisplay(Player player) {
        if(runningTasks.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(runningTasks.get(player.getUniqueId()));
            runningTasks.remove(player.getUniqueId());
        }
    }
    
    public void cleanup() {
        runningTasks.values().forEach(Bukkit.getScheduler()::cancelTask);
        runningTasks.clear();
        cooldowns.clear();
    }

    public void removeCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        cooldowns.remove(playerId);
        stopCooldownDisplay(player); // Đảm bảo dừng task hiển thị
    }
}