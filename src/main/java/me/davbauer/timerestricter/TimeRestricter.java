package me.davbauer.timerestricter;

import me.davbauer.timerestricter.commands.ForceOPCommand;
import me.davbauer.timerestricter.commands.GetPlayerInfo;
import me.davbauer.timerestricter.events.OnPlayerLoginEvent;
import me.davbauer.timerestricter.events.OnPlayerQuitEvent;
import me.davbauer.timerestricter.logic.ConfigFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TimeRestricter extends JavaPlugin {

    public TextOut txtout = new TextOut();
    Map<String, String> notify30 = new HashMap<String, String>();
    Map<String, String> notify20 = new HashMap<String, String>();
    Map<String, String> notify15 = new HashMap<String, String>();
    Map<String, String> notify10 = new HashMap<String, String>();
    Map<String, String> notify5 = new HashMap<String, String>();
    Map<String, String> notify2 = new HashMap<String, String>();

    @Override
    public void onEnable() {

        // Init all Commands
        getCommand("forceop").setExecutor(new ForceOPCommand());
        getCommand("info").setExecutor(new GetPlayerInfo(this));

        // Init all EventListeners
        getServer().getPluginManager().registerEvents(new OnPlayerLoginEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(this), this);

        // Get config data
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();

        txtout.sendGlobalMessageInfo( "active.");


        new BukkitRunnable() {

            int count = 0;
            @Override
            public void run() {
                count++;
                if (count >= 10) {
                    kickIfPlayerTimeOver(true);
                    count = 0;
                } else {
                    kickIfPlayerTimeOver(false);
                }

            }
        }.runTaskTimer(this, 0, 20 * 5);

    }

    public void kickIfPlayerTimeOver(boolean sendNotifications) {
        int minToMillisFactor = 60000;
        boolean configChanged = false;
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        // Checke fuer jeden Spieler
        for (final Player p : players) {
            String playerId = p.getUniqueId().toString();
            String playerDataPath = "data."+playerId;

            // If Player not in config skip to next player
            ConfigFunctions cf = new ConfigFunctions(this);
            if (!cf.dataForPlayerCreated(playerDataPath+".name")) continue;

            long availableMillis = getConfig().getLong("availableMinutes") * minToMillisFactor;
            long configSpent = getConfig().getLong(playerDataPath+".spent");
            long configJoined = getConfig().getLong(playerDataPath+".joined");
            long spentTimeTotal = (System.currentTimeMillis() - configJoined) + configSpent;

            // Player is over the play limit! kick!
            if (spentTimeTotal >= availableMillis) {
                txtout.sendGlobalMessageWarn(p.getName() + "`s time is over for today!");
                String configFillUpTime = getConfig().getString("fillUpTime");
                String msg = "[TimeRestricter]: " + ChatColor.RED + "Your time is up! " + ChatColor.AQUA + " Time refill at " + ChatColor.BLUE + configFillUpTime;
                p.kickPlayer(msg);
                continue;
            }

            if (!sendNotifications) continue;

            boolean over30 = getConfig().getBoolean(playerDataPath + ".notify.30");
            boolean over20 = getConfig().getBoolean(playerDataPath + ".notify.20");
            boolean over15 = getConfig().getBoolean(playerDataPath + ".notify.15");
            boolean over10 = getConfig().getBoolean(playerDataPath + ".notify.10");
            boolean over5 = getConfig().getBoolean(playerDataPath + ".notify.5");
            boolean over2 = getConfig().getBoolean(playerDataPath + ".notify.2");



            long leftOverMillis = availableMillis - spentTimeTotal;


            if (!over30 && leftOverMillis < 30 * minToMillisFactor) {
                txtout.sendPlayerMessageWarn("Attention! Less than 30 minutes left for today!", p);
                getConfig().set(playerDataPath + ".notify.30", true);
                configChanged = true;
            } else if (!over20 && leftOverMillis < 20 * minToMillisFactor) {
                txtout.sendPlayerMessageWarn("Attention! Less than 20 minutes left for today!", p);
                getConfig().set(playerDataPath + ".notify.20", true);
                configChanged = true;
            } else if (!over15 && leftOverMillis < 15 * minToMillisFactor) {
                txtout.sendPlayerMessageWarn("Attention! Less than 15 minutes left for today!", p);
                getConfig().set(playerDataPath + ".notify.15", true);
                configChanged = true;
            } else if (!over10 && leftOverMillis < 10 * minToMillisFactor) {
                txtout.sendPlayerMessageWarn("Attention! Less than 10 minutes left for today!", p);
                getConfig().set(playerDataPath + ".notify.10", true);
                configChanged = true;
            } else if (!over5 && leftOverMillis < 5 * minToMillisFactor) {
                txtout.sendPlayerMessageWarn("Attention! Less than 5 minutes left for today!", p);
                getConfig().set(playerDataPath + ".notify.5", true);
                configChanged = true;
            } else if (!over2 && leftOverMillis < 2 * minToMillisFactor) {
                txtout.sendPlayerMessageWarn("Attention! Only about 1 minute left for today!", p);
                getConfig().set(playerDataPath + ".notify.2", true);
                configChanged = true;
            }
        }
        if (!configChanged) return;
        saveConfig();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("[TimeRestricter] stopped.");
    }
}
