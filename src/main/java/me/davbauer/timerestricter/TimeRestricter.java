package me.davbauer.timerestricter;

import me.davbauer.timerestricter.commands.ForceOPCommand;
import me.davbauer.timerestricter.commands.GetPlayerInfo;
import me.davbauer.timerestricter.events.OnJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class TimeRestricter extends JavaPlugin {

    public TextOut txtout = new TextOut();
    @Override
    public void onEnable() {

        // Init all Commands
        getCommand("forceop").setExecutor(new ForceOPCommand());
        getCommand("info").setExecutor(new GetPlayerInfo(this));

        // Init all EventListeners
        getServer().getPluginManager().registerEvents(new OnJoinEvent(this), this);

        // Get config data
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        txtout.sendGlobalMessageInfo( "active.");


        new BukkitRunnable() {
            @Override
            public void run() {
                checkTimeOnEveryOne();
            }
        }.runTaskTimer(this, 0, 20 * 15);

        // 20 ticks is a second

    }


    public void checkTimeOnEveryOne() {
        List<Player> onlinePlayers = (List<Player>) Bukkit.getOnlinePlayers();
        if (onlinePlayers.size() == 0) return;
        List<String> existingList = (List<String>) getConfig().getList("livePlayerTime");

        for (final Player x : onlinePlayers) {
            Integer index = 0;
            for (final String data : existingList) {
                final String[] split = data.split(";");
                final String configPlayerName = split[0];
                final String configPlayerUUID = split[1];
                double configPlayerTime = Double.parseDouble(split[2]);

                if (x.getUniqueId().toString().equals(configPlayerUUID)) {
                    if(configPlayerTime < 0) {
                        x.kick();
                        break;
                    }
                    configPlayerTime -= 15;
                    existingList.set(index, configPlayerName + ";" + configPlayerUUID + ";" + configPlayerTime);
                }
                index++;
            }
        }
        getConfig().set("livePlayerTime", existingList);
        saveConfig();
    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("[TimeRestricter] stopped.");
    }
}
