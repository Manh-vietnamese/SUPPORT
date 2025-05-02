package com.example.commands;

import com.example.SupportServer;
import org.bukkit.command.Command; // Corrected import
import org.bukkit.command.CommandExecutor; // Corrected import
import org.bukkit.command.CommandSender; // Corrected import

public class ReloadCommand implements CommandExecutor {
    
    private final SupportServer plugin;
    
    public ReloadCommand(SupportServer plugin) {
        this.plugin = plugin; 
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        plugin.reloadPlugin(); 
        sender.sendMessage(plugin.getMessager().get("no_permission"));
        return true;
    }
}