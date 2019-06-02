package me.calebbassham.scenariomanager;

import me.calebbassham.settingsmanager.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scenario {

    private JavaPlugin plugin;

    private final String name;
    private boolean enabled = false;
    private boolean active = false;
    private HashSet<Listener> listeners = new HashSet<>();

    private SettingsManager settingsManager;

    public Scenario(String name) {
        this.name = name;

        if (this instanceof Listener) this.listeners.add((Listener) this);
    }

    public Scenario() {
        this.name = getNameFromClass(getClass());

        if (this instanceof Listener) this.listeners.add((Listener) this);
    }

    private String getNameFromClass(Class clazz) {
        ArrayList<String> nameParts = new ArrayList<>();
        Pattern p = Pattern.compile("[A-Z]+(?:[^A-Z]+)?");
        Matcher m = p.matcher(clazz.getSimpleName());
        while (m.find()) {
            String str = m.group(0);
            if (str.equalsIgnoreCase("setting")) continue;
            nameParts.add(str);
        }
        return String.join(" ", nameParts);
    }

    public String getName() {
        return this.name;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            if (ScenarioManager.isGameRunning()) setActive(true);
        } else {
            setActive(false);
        }
    }

    /**
     * Determines if a game is running and the scenario is enabled.
     * @return if a game is running and the scenario is enabled
     */
    public final boolean isActive() {
        return active;
    }

    public final void setActive(boolean active) {
        if (this.active == active) return;
        this.active = active;

        if (active) {
            this.registerListeners();
            Bukkit.getPluginManager().callEvent(new ScenarioStartEvent(this));
        } else {
            Bukkit.getPluginManager().callEvent(new ScenarioStopEvent(this));
            this.unregisterListeners();
        }
    }

    protected SettingsManager getSettingsManager() {
        return settingsManager;
    }

    protected void addListener(Listener listener) {
        this.listeners.add(listener);
        // TODO: Register if necessary
    }

    protected void removeListener(Listener listener) {
        this.listeners.remove(listener);
        HandlerList.unregisterAll(listener);
    }

    void registerListeners() {
        listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, plugin));
    }

    void unregisterListeners() {
        listeners.forEach(HandlerList::unregisterAll);
    }

    void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    JavaPlugin getPlugin() {
        return this.plugin;
    }

}
