package me.davbauer.timerestricter.logic;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResetTimeForPlayers {

    final TimeRestricter main;


    public ResetTimeForPlayers(TimeRestricter main) {
        this.main = main;
    }

    public void doRoutine() {
        List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();

        // Set joined for every Player to null,
        // Remove any spent time on the Server
        for (final String playerId : main.getConfig().getConfigurationSection("data").getKeys(false)) {
            String playerPath = "data." + playerId;
            main.getConfig().set(playerPath + ".joined", null);
            main.getConfig().set(playerPath + ".spent", null);
        }

        long newJoinTime = System.currentTimeMillis();

        // For every Player still online set joined to now!
        for (final Player p : Bukkit.getOnlinePlayers()) {
            String playerPath = "data." + p.getUniqueId().toString();
            main.getConfig().set(playerPath + ".joined", newJoinTime);
        }

        if(!main.getConfig().getBoolean("enabled")) {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                main.onPlayerQuitEvent.PlayerOffline(p);
            }
        }

        // Clear notificationList for future notifications
        main.checkTimeOnPlayers.cleanRememberLists();
        main.saveConfig();

        String msgout = "Time has been filled up for everyone!";
        main.txtout.sendGlobalMessageInfo(msgout);
        Bukkit.getConsoleSender().sendMessage(msgout);
    }
}
