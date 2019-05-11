package me.calebbassham.scenariomanager;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class PlayerStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    public PlayerStartEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
