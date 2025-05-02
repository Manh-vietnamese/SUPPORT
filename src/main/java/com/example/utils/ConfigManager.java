package com.example.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConfigManager {
    
    private int cooldownSeconds;
    private String cooldownMessage;
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private Set<String> blockedCommands = new HashSet<>();
    private Map<String, String> placeholders = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig(); // Thêm dòng này để khởi tạo config
        reloadConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        
        blockedCommands = new HashSet<>(config.getStringList("blocked-commands"));
        cooldownSeconds = config.getInt("cooldown-seconds", 30); // Chỉ đọc cooldown-seconds từ config.yml
    }

    public int getCooldownSeconds() {return cooldownSeconds;}
    public String getCooldownMessage() {return cooldownMessage;}
    public Set<String> getBlockedCommands() {return blockedCommands;}
    public Map<String, String> getPlaceholders() {return new HashMap<>(placeholders);}
}