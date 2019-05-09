package me.calebbassham.scenariomanager;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ScenarioManager {

    private ScenarioManager() {}

    private static HashMap<String, Scenario> scenarios;

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

}
