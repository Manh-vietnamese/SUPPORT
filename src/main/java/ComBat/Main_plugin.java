package ComBat;

import ComBat.Manager.CB_Config;
import ComBat.Manager.CB_Cooldown;
import ComBat.Messager.Messager;
import ComBat.listeners.CB_Damage;
import ComBat.listeners.CB_PlayerCommand;
import ComBat.listeners.CB_PlayerDeath;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main_plugin extends JavaPlugin {
    private Messager Messager;
    private CB_Config configManager;
    private CB_Cooldown cooldownManager;
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        // Check for ProtocolLib first
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().severe("ProtocolLib không được cài đặt! Plugin sẽ tắt.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        // Initialize config and cooldown managers after ProtocolLib check
        saveDefaultConfig();
        checkAndCreateConfig();

        this.Messager = new Messager(getDataFolder());
        this.configManager = new CB_Config(this);
        this.cooldownManager = new CB_Cooldown(this);
        
        // Đăng ký reload command
        this.getCommand("supportserver").setExecutor(new ReloadCommand(this));

        // Register listeners and commands
        getServer().getPluginManager().registerEvents(new CB_Damage(this), this);
        getServer().getPluginManager().registerEvents(new CB_PlayerCommand(this), this);
        getServer().getPluginManager().registerEvents(new CB_PlayerDeath(this), this);
    }

    @Override
    public void onDisable() {
        if (cooldownManager != null) {
            cooldownManager.cleanup();
        } 
    }

    // Thêm phương thức reload toàn bộ
    public void reloadPlugin() {
        configManager.reloadConfig();
        Messager.reload(); // Reload messages.yml
        getLogger().info("Plugin đã được reload!");
    }

    // Getter methods
    public CB_Config getConfigManager() {
        return configManager;
    }

    public CB_Cooldown getCooldownManager() {
        return cooldownManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
    
    public Messager getMessager() {
        return Messager;
    }

    // Inner class xử lý lệnh reload
    private static class ReloadCommand implements org.bukkit.command.CommandExecutor {
        private final Main_plugin plugin;

        public ReloadCommand(Main_plugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
            if (!sender.hasPermission("Sunflower.SP.admin")) {
                sender.sendMessage(plugin.getMessager().get("no_permission"));
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                plugin.reloadPlugin();
                sender.sendMessage(plugin.getMessager().get("Plugin_reloaded"));
                return true;
            }
            return false;
        }
    }
        private void checkAndCreateConfig() {
        createFile("config.yml", true);
        createFile("messages.yml", false);
    }

    private void createFile(String fileName, boolean isConfig) {
        File file = new File(getDataFolder(), fileName);
        if (!file.exists()) {
            getLogger().warning("⚠️ Không tìm thấy " + fileName + "! Đang tạo file mới...");
            if (isConfig) {
                saveDefaultConfig();
                reloadConfig();
            } else {
                saveResource(fileName, false);
            }
            getLogger().info("✅ File " + fileName + " đã được khôi phục!");
        }
    }
}