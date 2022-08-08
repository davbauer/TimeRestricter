package me.davbauer.timerestricter.events;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class OnPlayerLoginEvent implements Listener {

    final TimeRestricter main;

    public OnPlayerLoginEvent(TimeRestricter main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        Bukkit.getConsoleSender().sendMessage(p.getName());


    }

}
