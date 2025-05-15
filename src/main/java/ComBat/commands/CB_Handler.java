/**
 * Lớp xử lý lệnh reload plugin (/supportserver reload).
 * Implement CommandExecutor để bắt sự kiện thực thi lệnh.
*/

package ComBat.commands;

import ComBat.Main_plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CB_Handler implements CommandExecutor {
    private final Main_plugin plugin; 
    
    public CB_Handler(Main_plugin plugin) {
        this.plugin = plugin; // Lưu trữ tham chiếu plugin
    }

    /**
     * Phương thức xử lý khi lệnh được thực thi.
     * @param sender Đối tượng gửi lệnh (người chơi hoặc console).
     * @param cmd Đối tượng Command.
     * @param label Tên lệnh được sử dụng.
     * @param args Các đối số của lệnh.
     * @return true nếu xử lý thành công, false nếu có lỗi.
    */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Reload toàn bộ cấu hình và messages
        plugin.reloadPlugin(); 
        
        // Gửi thông báo reload thành công
        sender.sendMessage(plugin.getMessager().get("Plugin_reloaded")); // Đã sửa từ "no_permission" -> "Plugin_reloaded"
        
        return true; // Trả về true để xác nhận lệnh đã xử lý
    }
}