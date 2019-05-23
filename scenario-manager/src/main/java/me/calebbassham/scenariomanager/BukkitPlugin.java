package me.calebbassham.scenariomanager;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ScenarioManager(), this);
        registerCmd("scenariomanager", new ScenarioManagerCmd());
    }

    private void registerCmd(String name, CommandExecutor cmd) {
        PluginCommand pCmd = getCommand(name);
        pCmd.setExecutor(cmd);
        if (cmd instanceof TabCompleter) pCmd.setTabCompleter((TabCompleter) cmd);
    }

    @Override
    public void onDisable() {
        ScenarioManager.reset();
    }
}
