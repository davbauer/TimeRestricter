package me.davbauer.timerestricter;

import me.davbauer.timerestricter.commands.ForceOPCommand;
import me.davbauer.timerestricter.commands.GetPlayerInfo;
import me.davbauer.timerestricter.commands.GetServerTime;
import me.davbauer.timerestricter.commands.SetFillUpTime;
import me.davbauer.timerestricter.events.OnPlayerLoginEvent;
import me.davbauer.timerestricter.events.OnPlayerQuitEvent;
import me.davbauer.timerestricter.logic.CheckIfTimeToReset;
import me.davbauer.timerestricter.logic.CheckXTimesOnPlayer;
import me.davbauer.timerestricter.logic.ConfigFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TimeRestricter extends JavaPlugin {

    public TextOut txtout = new TextOut();
    public CheckXTimesOnPlayer checkOnPlayerTimes = new CheckXTimesOnPlayer(this);
    public CheckIfTimeToReset checkTimeToReset = new CheckIfTimeToReset(this);

    @Override
    public void onEnable() {

        // Init all Commands
        getCommand("forceop").setExecutor(new ForceOPCommand());
        getCommand("time").setExecutor(new GetPlayerInfo(this));
        getCommand("getservertime").setExecutor(new GetServerTime(this));
        getCommand("setfilluptime").setExecutor(new SetFillUpTime(this));

        // Init all EventListeners
        getServer().getPluginManager().registerEvents(new OnPlayerLoginEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(this), this);

        // Get config data
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();

        txtout.sendGlobalMessageInfo( "active.");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (checkTimeToReset.checkRoutine()) {
                    checkOnPlayerTimes.cleanRememberLists();

                    saveConfig();
                }
                checkOnPlayerTimes.checkRoutine();
            }
        }.runTaskTimer(this, 0, 20 * 15);

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("[TimeRestricter] stopped.");
    }
}
