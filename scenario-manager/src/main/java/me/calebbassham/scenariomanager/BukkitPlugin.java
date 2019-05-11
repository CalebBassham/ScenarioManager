package me.calebbassham.scenariomanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ScenarioManager(), this);
    }

    @Override
    public void onDisable() {
        ScenarioManager.reset();
    }
}
