package me.davbauer.timerestricter.logic;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CheckTimeOnPlayers {

    private final TimeRestricter main;
    private final LogicFunctions cf;

    private int  minToMillisFactor = 60000;

    private Map<String, Integer> notifyReminder = new HashMap<>();
    public CheckTimeOnPlayers(TimeRestricter main) {
        this.main = main;
        this.cf = new LogicFunctions(main);
    }

    public void cleanRememberLists() {
        this.notifyReminder.clear();
    }

    public void checkRoutine() {
        // Check for every player online
        for (final Player p : Bukkit.getOnlinePlayers()) {
            String playerId = p.getUniqueId().toString();
            String playerDataPath = "data."+playerId;

            // If Player not in config skip to next player
            LogicFunctions cf = new LogicFunctions(main);
            if (!cf.dataForPlayerCreated(playerDataPath+".name")) continue;

            long availableMillis = main.getConfig().getLong("availableMinutes") * minToMillisFactor;
            long configSpent = main.getConfig().getLong(playerDataPath+".spent");
            long configJoined = main.getConfig().getLong(playerDataPath+".joined");
            long spentTimeTotal = (System.currentTimeMillis() - configJoined) + configSpent;

            // Player is over the play limit! kick!
            if (spentTimeTotal >= availableMillis) {
                main.txtout.sendGlobalMessageWarn(p.getName() + "`s time is over for today!");
                p.kickPlayer(cf.generateKickMessage());
                continue;
            }

            long leftOverMillis = availableMillis - spentTimeTotal;

            if(!this.notifyReminder.containsKey(playerId)) this.notifyReminder.put(playerId, 1000000);

            int currNot = this.notifyReminder.get(playerId);

            if (leftOverMillis < 1 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than [1] minute left for today!", p);
            } else if (currNot > 2 && leftOverMillis < 2 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Only about [1] minute left for today!", p);
                this.notifyReminder.put(playerId, 2);
            } else if (currNot > 5 && leftOverMillis < 5 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Only about [5] minutes left for today!", p);
                this.notifyReminder.put(playerId, 5);
            } else if (currNot > 10 && leftOverMillis < 10 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than [10] minutes left for today!", p);
                this.notifyReminder.put(playerId, 10);
            } else if (currNot > 15 && leftOverMillis < 15 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than [15] minutes left for today!", p);
                this.notifyReminder.put(playerId, 15);
            } else if (currNot > 20 && leftOverMillis < 20 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than [20] minutes left for today!", p);
                this.notifyReminder.put(playerId, 20);
            } else if (currNot > 30 && leftOverMillis < 30 * minToMillisFactor) {
                main.txtout.sendPlayerMessageWarn("Attention! Less than [30] minutes left for today!", p);
                this.notifyReminder.put(playerId, 30);
            }
        }
    }
}
