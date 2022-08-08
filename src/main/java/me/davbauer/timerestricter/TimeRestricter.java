package me.davbauer.timerestricter;

import me.davbauer.timerestricter.commands.ForceOPCommand;
import me.davbauer.timerestricter.commands.GetPlayerInfo;
import me.davbauer.timerestricter.events.OnPlayerLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TimeRestricter extends JavaPlugin {

    public TextOut txtout = new TextOut();
    @Override
    public void onEnable() {

        // Init all Commands
        getCommand("forceop").setExecutor(new ForceOPCommand());
        getCommand("info").setExecutor(new GetPlayerInfo(this));

        // Init all EventListeners
        getServer().getPluginManager().registerEvents(new OnPlayerLoginEvent(this), this);

        // Get config data
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        txtout.sendGlobalMessageInfo( "active.");

        /*
        new BukkitRunnable() {
            int count = 0;
            int checkSeconds = 30;
            @Override
            public void run() {
                if (count > checkSeconds * 4) {
                    checkTimeOnEveryPlayer(checkSeconds, true);
                    txtout.sendGlobalMessageInfo("saved file + updated");
                    count = 0;
                } else {
                    checkTimeOnEveryPlayer(checkSeconds, false);
                    txtout.sendGlobalMessageInfo("updated");
                }

                count++;
            }
        }.runTaskTimer(this, 0, 20 * 30);

        // 20 ticks is a second

         */

    }

/*
    public void checkTimeOnEveryPlayer(int deductTime, boolean saveFile) {
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
                    configPlayerTime -= deductTime;
                    existingList.set(index, configPlayerName + ";" + configPlayerUUID + ";" + configPlayerTime);
                }
                index++;
            }
        }
        getConfig().set("livePlayerTime", existingList);

        if(!saveFile) return;
        saveConfig();
    }

 */




    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("[TimeRestricter] stopped.");
    }
}
