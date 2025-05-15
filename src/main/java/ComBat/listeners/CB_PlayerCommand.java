/**
 * Lớp xử lý sự kiện khi người chơi thực hiện lệnh trong game.
 * Kiểm tra và chặn các lệnh đã cấu hình nếu người chơi đang trong trạng thái cooldown.
 */

package ComBat.listeners;

import ComBat.Main_plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CB_PlayerCommand implements Listener {
    private final Main_plugin plugin; 

    public CB_PlayerCommand(Main_plugin plugin) {
        this.plugin = plugin; 
    }

    /**
     * Xử lý sự kiện khi người chơi thực hiện lệnh.
     * Hủy sự kiện nếu lệnh thuộc danh sách bị chặn và người chơi đang trong cooldown.
     * @param event Sự kiện PlayerCommandPreprocessEvent
     */
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        // Trích xuất tên lệnh (bỏ qua dấu "/" và các tham số)
        String rawCommand = event.getMessage().substring(1).split(" ")[0].toLowerCase();

        // Kiểm tra điều kiện chặn lệnh
        if (shouldBlockCommand(player, rawCommand)) {
            event.setCancelled(true); // Hủy thực thi lệnh
        }
    }

    /**
     * Kiểm tra xem lệnh có nên bị chặn hay không dựa trên:
     * - Người chơi đang trong cooldown
     * - Lệnh nằm trong danh sách bị chặn (config.yml)
     * - Người chơi KHÔNG có quyền admin
     * @param player Người chơi thực hiện lệnh
     * @param command Tên lệnh đã được chuẩn hóa (lowercase, không chứa "/")
     * @return true nếu lệnh bị chặn, ngược lại trả về false
     */
    private boolean shouldBlockCommand(Player player, String command) {
        return plugin.getCooldownManager().isInCooldown(player) // Đang cooldown
                && plugin.getConfigManager().getBlockedCommands().contains(command) // Lệnh bị chặn
                && !player.hasPermission("Sunflower.SP.admin"); // Không có quyền admin
    }
}