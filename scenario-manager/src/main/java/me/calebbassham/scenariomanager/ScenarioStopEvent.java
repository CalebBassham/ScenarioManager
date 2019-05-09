package me.calebbassham.scenariomanager;

import org.bukkit.event.HandlerList;

public class ScenarioStopEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
