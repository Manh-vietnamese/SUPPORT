/**
 * Xử lý sự kiện nhận sát thương của người chơi:
 * - Áp dụng cooldown và hiển thị thời gian chờ nếu người chơi bị tấn công.
 * @param event Sự kiện EntityDamageEvent.
 */

package ComBat.listeners;

import ComBat.Main_plugin; 
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CB_Damage implements Listener {
    private final Main_plugin plugin;

    public CB_Damage(Main_plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        // Bỏ qua nếu thực thể không phải Player hoặc sự kiện đã bị hủy
        if (!(event.getEntity() instanceof Player player) || event.isCancelled()) return;
        
        // Kích hoạt cooldown và hiển thị Action Bar
        plugin.getCooldownManager().applyCooldown(player);
        plugin.getCooldownManager().startCooldownDisplay(player);
    }

    /**
     * Xử lý sự kiện thực thi lệnh của người chơi:
     * - Chặn lệnh nếu người chơi đang trong cooldown và không có quyền admin.
     * @param event Sự kiện PlayerCommandPreprocessEvent.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        
        // Bỏ qua nếu người chơi có quyền admin
        if (player.hasPermission("Sunflower.SP.admin")) return;
    
        // Trích xuất tên lệnh (ví dụ: "/msg" -> "msg")
        String rawCommand = event.getMessage().substring(1).split(" ")[0].toLowerCase();
        
        // Kiểm tra lệnh có bị chặn và đang trong cooldown
        if (plugin.getConfigManager().getBlockedCommands().contains(rawCommand) && 
            plugin.getCooldownManager().isInCooldown(player)) {
            event.setCancelled(true); // Hủy sự kiện
            player.sendMessage(plugin.getMessager().get("no_command")); // Gửi thông báo
        }
    }
}