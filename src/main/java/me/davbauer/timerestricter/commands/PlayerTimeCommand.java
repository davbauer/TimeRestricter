package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PlayerTimeCommand implements CommandExecutor {

    private final TimeRestricter main;
    private final LogicFunctions lf;

    public PlayerTimeCommand(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!lf.pluginIsEnabledWithMsg(sender)) return false;

        if (args.length == 0) {
            if(!lf.senderAllowedBasicCommands()) {
                if (!lf.senderGotRights("timerestricter.view_time_self", sender)) return false;
            }
            if(sender instanceof Player) {
                Player p = (Player) sender;
                String outputmsg = getResponse(p.getName(), p.getUniqueId().toString());
                main.txtout.sendPlayerMessageInfo(outputmsg, p);
                return true;
            } else {
                lf.sendToConsole("You need to be a player or specify one to use this command.");
            }

        } else {

            if(!lf.senderAllowedBasicCommands()) {
                if (!lf.senderGotRights("timerestricter.view_time_others", sender)) return false;
            }
            String playerNameToSearch = args[0];
            String playerId = "";

            for (final String uuid : main.getConfig().getConfigurationSection("data").getKeys(false)) {
                String currName = main.getConfig().getString("data." + uuid + ".name");
                if (currName.equals(playerNameToSearch)) {
                    playerId = uuid;
                    break;
                }
            }

            if (playerId.length() == 0) {
                lf.sendMsgToCorrectSenderWarn("Player " + playerNameToSearch + " not found.", sender);
                return false;
            } else {
                lf.sendMsgToCorrectSenderInfo(getResponse(playerNameToSearch, playerId), sender);
                return true;
            }
        }
        return false;
    }

    public String getResponse(String playerName, String playerId) {

        long joinedMillis = main.getConfig().getLong("data."+playerId+".joined");
        long configTime = main.getConfig().getLong("availableMinutes") * 60000; // Convert Minutes in Config to Millis
        long spentTime = main.getConfig().getLong("data."+playerId+".spent");

        // Check if joined time in config valid
        if (joinedMillis == 0) return "";
        long currentMillis = System.currentTimeMillis();

        // calculate millis (available time - already spent time - current session time)
        long calculatedMillis = configTime - spentTime - (currentMillis - joinedMillis);
        String minutesLeft = String.format("%dm, %ds",
                TimeUnit.MILLISECONDS.toMinutes(calculatedMillis),
                TimeUnit.MILLISECONDS.toSeconds(calculatedMillis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(calculatedMillis))
        );

        return playerName + "`s leftover time: " + minutesLeft + ".";
    }
}
