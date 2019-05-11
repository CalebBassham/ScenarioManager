package me.calebbassham.scenariomanager;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class ScenarioStopEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private Scenario scenario;

    ScenarioStopEvent(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }
}
