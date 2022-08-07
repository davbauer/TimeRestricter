package me.davbauer.timerestricter.events;

import me.davbauer.timerestricter.TimeRestricter;
import org.apache.logging.log4j.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class OnJoinEvent implements Listener {

    final TimeRestricter main;

    public OnJoinEvent(TimeRestricter main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        String currentPlayerNameUUID = event.getPlayer().getUniqueId().toString();
        String currentPlayerName = event.getPlayer().getName();
        boolean playerAlreadyInList = false;

        List<String> existingList = (List<String>) main.getConfig().getList("livePlayerTime");
        int index = 0;
        for (final String x : existingList) {
            final String[] split = x.split(";");
            final String configPlayerNameUUID = split[1];

            if (configPlayerNameUUID.equals(currentPlayerNameUUID)) {
                final double configPlayerTime = Double.parseDouble(split[2]);
                playerAlreadyInList = true;
                if(configPlayerTime < 0) {
                    event.getPlayer().kick();
                    break;
                }



                main.txtout.sendPlayerMessageInfo(configPlayerTime/60 + "m remaining.", event.getPlayer());
                final String configPlayerName = split[0];
                if (!configPlayerName.equals(currentPlayerName)) {

                    existingList.set(index, currentPlayerName + ";" + currentPlayerNameUUID + ";" + configPlayerTime);
                    main.getConfig().set("livePlayerTime", existingList);
                    main.saveConfig();
                }
                break;
            }
            index++;
        }

        if(playerAlreadyInList) return;

        double availableTimeInMinutes = main.getConfig().getDouble("availableTimeInMinutes");

        main.txtout.sendPlayerMessageInfo(availableTimeInMinutes + "m remaining.", event.getPlayer());

        existingList.add(currentPlayerName + ";" + currentPlayerNameUUID + ";" + availableTimeInMinutes * 60);
        main.getConfig().set("livePlayerTime", existingList);
        main.saveConfig();


    }

}
