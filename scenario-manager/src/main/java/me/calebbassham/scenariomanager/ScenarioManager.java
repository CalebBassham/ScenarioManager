package me.calebbassham.scenariomanager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class ScenarioManager implements Listener {

    ScenarioManager() { }

    private static boolean gameRunning = false;
    private static HashMap<String, Scenario> scenarios = new HashMap<>();

    private static Supplier<Collection<World>> worldsSupplier = Bukkit::getWorlds;

    public static void register(Scenario scenario, JavaPlugin plugin) {
        scenario.setPlugin(plugin);
        scenarios.put(scenario.getName().toLowerCase(), scenario);
    }

    public static void unregister(Scenario scenario) {
        unregister(scenario.getName().toLowerCase());
    }

    public static void unregister(String name) {
        Scenario scenario = scenarios.remove(name);
        if (scenario != null) {
            scenario.setPlugin(null);
        }
    }

    public static Scenario getScenario(String name) {
        return scenarios.get(name.toLowerCase());
    }

    public static <T extends Scenario> Scenario getScenario(final Class<T> scenario) {
        return scenarios.values().stream()
                .filter(s -> s.getClass().equals(scenario))
                .findFirst()
                .orElse(null);
    }

    public static CompletableFuture<Void> assignTeams(Player[] players) {
        AssignTeams teamAssigner = scenarios.values().stream()
                .filter(Scenario::isEnabled)
                .filter(scenario -> scenario instanceof AssignTeams)
                .map(scenario -> (AssignTeams) scenario)
                .findAny()
                .orElse(null);

        if (teamAssigner == null) return CompletableFuture.completedFuture(null);

        return teamAssigner.assignTeams(players);
    }

    static void reset() {
        scenarios.values().forEach(Scenario::unregisterListeners);
        scenarios.clear();
    }

    public static void startGame() {
        gameRunning = true;
        scenarios.values().stream()
                .filter(Scenario::isEnabled)
                .forEach(scenario -> scenario.setActive(true));
    }

    public static void stopGame() {
        gameRunning = false;
        scenarios.values().stream()
                .filter(Scenario::isActive)
                .forEach(scenario -> scenario.setActive(false));
    }

    public static boolean isGameRunning() {
        return gameRunning;
    }

    public static Collection<Scenario> getScenarios() {
        return scenarios.values();
    }

    public static Collection<World> getWorlds() {
        return worldsSupplier.get();
    }

    public static boolean isWorld(World world) {
        return getWorlds().contains(world);
    }

    public static void setWorldsSupplier(Supplier<Collection<World>> supplier) {
        worldsSupplier = supplier;
    }

    @EventHandler
    public void onDisablePlugin(PluginDisableEvent e) {
        Iterator iter = scenarios.keySet().iterator();
        while (iter.hasNext()) {
            unregister((String) iter.next());
        }
    }
}
