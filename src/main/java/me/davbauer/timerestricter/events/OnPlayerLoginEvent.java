package me.davbauer.timerestricter.events;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.commands.PlayerTimeCommand;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;


public class OnPlayerLoginEvent implements Listener {

    private final TimeRestricter main;
    private final LogicFunctions lf;

    public OnPlayerLoginEvent(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        if (!main.getConfig().getBoolean("enabled")) return;
        // Send player how much time he got left onJoin
        PlayerTimeCommand infoCommandObject = new PlayerTimeCommand(main);
        infoCommandObject.sendResponse(e.getPlayer());
    }


    @EventHandler
    public void PlayerLoginEvent(PlayerLoginEvent e) {
        if (!main.getConfig().getBoolean("enabled")) return;

        Player p = e.getPlayer();

        String playerName = p.getName();
        String playerId = p.getUniqueId().toString();
        String playerDataPath = "data."+playerId;



        // Check if player already exists
        if(lf.dataForPlayerCreated(playerDataPath+".name")) {

            // If Player exists, just check if display name still the same, if not update it!
            String configName = main.getConfig().getString(playerDataPath+".name");
            if (!configName.equals(playerName)) {
                main.getConfig().set(playerDataPath+".name", playerName);
            }

            // Check if Player is allowed to join (time up or not?)
            long configSpent = main.getConfig().getLong(playerDataPath+".spent");
            long configMillis = main.getConfig().getLong("availableMinutes") * 60000; // convert minutes to millis
            if (configSpent >= configMillis) {
                e.disallow(PlayerLoginEvent.Result.KICK_FULL, lf.generateKickMessage());
            }

            main.getConfig().set(playerDataPath+".joined", System.currentTimeMillis());
            main.saveConfig();

            return;
        }

        // If player does not exist, create player-data in config

        double availableMinutes = main.getConfig().getDouble("availableMinutes");

        main.getConfig().set(playerDataPath+".name", playerName);
        main.getConfig().set(playerDataPath+".joined", System.currentTimeMillis());
        main.saveConfig();

    }

}
