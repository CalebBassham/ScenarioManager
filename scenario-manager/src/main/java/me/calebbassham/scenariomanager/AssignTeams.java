package me.calebbassham.scenariomanager;

import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface AssignTeams {

    CompletableFuture<Void> assignTeams(Player[] players);

}
