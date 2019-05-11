package me.calebbassham.scenariomanager;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class ScenarioStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Scenario scenario;

    ScenarioStartEvent(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
