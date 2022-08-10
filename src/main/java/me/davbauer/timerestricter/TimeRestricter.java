package me.davbauer.timerestricter;

import me.davbauer.timerestricter.commands.*;
import me.davbauer.timerestricter.events.OnPlayerLoginEvent;
import me.davbauer.timerestricter.events.OnPlayerQuitEvent;
import me.davbauer.timerestricter.logic.CheckIfTimeToReset;
import me.davbauer.timerestricter.logic.CheckTimeOnPlayers;
import me.davbauer.timerestricter.logic.ResetTimeForPlayers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;

public final class TimeRestricter extends JavaPlugin {

    public TextOut txtout = new TextOut();
    public CheckTimeOnPlayers checkTimeOnPlayers = new CheckTimeOnPlayers(this);
    public CheckIfTimeToReset checkIfTimeToReset = new CheckIfTimeToReset(this);
    public ResetTimeForPlayers resetTimeForPlayers = new ResetTimeForPlayers(this);

    @Override
    public void onEnable() {

        // Init all Commands
        getCommand("tr-enabled").setExecutor(new EnabledCommand(this));
        getCommand("tr-credits").setExecutor(new CreditsInfoCommand(this));
        getCommand("tr-timereset").setExecutor(new ResetTimeCommand(this));
        getCommand("tr-forceop").setExecutor(new ForceOPCommand());
        getCommand("tr-time").setExecutor(new PlayerTimeCommand(this));
        getCommand("tr-servertime").setExecutor(new ServerTimeCommand(this));
        getCommand("tr-filluptime").setExecutor(new FillUpTimeCommand(this));
        getCommand("tr-availableminutes").setExecutor(new AvailableMinutesCommand(this));
        getCommand("tr-playerstime").setExecutor(new PlayersTimeCommand(this));

        // Get config data
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();

        // Check if plugin should even be enabled
        boolean shouldBeEnabled =  getConfig().getBoolean("enabled");

        if (!shouldBeEnabled) return;


        // Init all EventListeners
        getServer().getPluginManager().registerEvents(new OnPlayerLoginEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(this), this);



        txtout.sendGlobalMessageInfo( "active.");

        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (checkIfTimeToReset.checkRoutine()) {
                    resetTimeForPlayers.doRoutine();
                }
                checkTimeOnPlayers.checkRoutine();
            }
        }, 60, 20*15);

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("[TimeRestricter] stopped.");
    }
}
