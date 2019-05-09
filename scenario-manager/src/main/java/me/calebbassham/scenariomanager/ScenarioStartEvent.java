package me.calebbassham.scenariomanager;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ScenarioStartEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player[] players;

    public ScenarioStartEvent(Player[] players) {
        this.players = players;
    }

    public Player[] getPlayers() {
        return players;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
