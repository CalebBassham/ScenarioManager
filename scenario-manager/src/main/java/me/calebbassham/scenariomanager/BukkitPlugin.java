package me.calebbassham.scenariomanager;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onDisable() {
        ScenarioManager.reset();
    }
}
