package me.davbauer.timerestricter.logic;

import me.davbauer.timerestricter.TextOut;
import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CheckXTimesOnPlayer {

    private final TimeRestricter main;
    private final ConfigFunctions cf;

    private int  minToMillisFactor = 60000;

    private List<String> notify30 = new ArrayList<String>();
    private List<String> notify20 = new ArrayList<String>();
    private List<String> notify15 = new ArrayList<String>();
    private List<String> notify10 = new ArrayList<String>();
    private List<String> notify5 = new ArrayList<String>();
    private List<String> notify2 = new ArrayList<String>();

    public CheckXTimesOnPlayer(TimeRestricter main) {
        this.main = main;
        this.cf = new ConfigFunctions(main);
    }

    public void cleanRememberLists() {
        this.notify30.clear();
        this.notify20.clear();
        this.notify15.clear();
        this.notify10.clear();
        this.notify5.clear();
        this.notify2.clear();
    }

    public void checkRoutine() {
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();

        // Check for every player online
        for (final Player p : players) {
            String playerId = p.getUniqueId().toString();
            String playerDataPath = "data."+playerId;

            // If Player not in config skip to next player
            ConfigFunctions cf = new ConfigFunctions(main);
            if (!cf.dataForPlayerCreated(playerDataPath+".name")) continue;

            long availableMillis = main.getConfig().getLong("availableMinutes") * minToMillisFactor;
            long configSpent = main.getConfig().getLong(playerDataPath+".spent");
            long configJoined = main.getConfig().getLong(playerDataPath+".joined");
            long spentTimeTotal = (System.currentTimeMillis() - configJoined) + configSpent;

            // Player is over the play limit! kick!
            if (spentTimeTotal >= availableMillis) {
                main.txtout.sendGlobalMessageWarn(p.getName() + "`s time is over for today!");
                String configFillUpTime = main.getConfig().getString("fillUpTime");
                String msg = "[TimeRestricter]: " + ChatColor.RED + "Your time is up! " + ChatColor.AQUA + " Time refill at " + ChatColor.BLUE + configFillUpTime;
                p.kickPlayer(msg);
                continue;
            }

            long leftOverMillis = availableMillis - spentTimeTotal;

            if ((!this.notify30.contains(playerId)) && leftOverMillis < 30 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than 30 minutes left for today!", p);
                this.notify30.add(playerId);
            } else if ((!this.notify20.contains(playerId)) && leftOverMillis < 20 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than 20 minutes left for today!", p);
                this.notify20.add(playerId);
            } else if ((!this.notify15.contains(playerId)) && leftOverMillis < 15 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than 15 minutes left for today!", p);
                this.notify15.add(playerId);
            } else if ((!this.notify10.contains(playerId)) && leftOverMillis < 10 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than 10 minutes left for today!", p);
                this.notify10.add(playerId);
            } else if ((!this.notify5.contains(playerId)) && leftOverMillis < 5 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than 5 minutes left for today!", p);
                this.notify5.add(playerId);
            } else if ((!this.notify2.contains(playerId)) && leftOverMillis < 2 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Only about 1 minute left for today!", p);
                this.notify2.add(playerId);
            }
        }
    }
}
