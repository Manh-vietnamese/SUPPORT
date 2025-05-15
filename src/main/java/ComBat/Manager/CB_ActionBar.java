package ComBat.Manager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import ComBat.Main_plugin;

import java.util.logging.Level;

import org.bukkit.entity.Player;

public class CB_ActionBar {
    private final ProtocolManager protocolManager;
    private final Main_plugin plugin;

    public CB_ActionBar(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
        this.plugin = Main_plugin.getPlugin(Main_plugin.class);
    }

    public void sendCustomActionBar(Player player, String message) { // Bỏ tham số yOffset không dùng
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
    
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Lỗi gửi Action Bar: " + e.getMessage(), e);
        }
    }
}