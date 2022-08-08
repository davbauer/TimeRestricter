package me.davbauer.timerestricter.events;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.ConfigFunctions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class OnPlayerQuitEvent implements Listener {

    private final TimeRestricter main;
    private final ConfigFunctions cf;

    public OnPlayerQuitEvent(TimeRestricter main) {
        this.main = main;
        this.cf = new ConfigFunctions(main);
    }


    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        String playerId = p.getUniqueId().toString();
        String playerDataPath = "data."+playerId;

        // Check if player already exists
        if(cf.dataForPlayerCreated(playerDataPath+".name")) {
            long alreadySpentTime = main.getConfig().getLong(playerDataPath+".spent");
            long currentTime = System.currentTimeMillis();
            long joinedTime = main.getConfig().getLong(playerDataPath+".joined");

            if (joinedTime == 0) return;

            long calculatedTime = currentTime - joinedTime;
            main.getConfig().set(playerDataPath+".spent", alreadySpentTime + calculatedTime);
            main.getConfig().set(playerDataPath+".joined", null);
            main.saveConfig();
        }
    }

}
