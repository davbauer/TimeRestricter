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

        if(!lf.senderAllowedBasicCommands()) {
            if (!lf.senderGotRights("timerestricter.view_time_self", sender)) return false;
        }

        if (args.length == 0) {
            if(sender instanceof Player) {
                return sendResponse(((Player) sender).getPlayer());
            }
            lf.sendToConsole("You need to be a player to use this command or specify one.");
        }
        return false;
    }

    public boolean sendResponse(Player p) {
        String playerId = p.getUniqueId().toString();

        long joinedMillis = main.getConfig().getLong("data."+playerId+".joined");
        long configTime = main.getConfig().getLong("availableMinutes") * 60000; // Convert Minutes in Config to Millis
        long spentTime = main.getConfig().getLong("data."+playerId+".spent");

        // Check if joined time in config valid
        if (joinedMillis == 0) return false;
        long currentMillis = System.currentTimeMillis();

        // calculate millis (available time - already spent time - current session time)
        long calculatedMillis = configTime - spentTime - (currentMillis - joinedMillis);
        String minutesLeft = String.format("%dm, %ds",
                TimeUnit.MILLISECONDS.toMinutes(calculatedMillis),
                TimeUnit.MILLISECONDS.toSeconds(calculatedMillis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(calculatedMillis))
        );

        main.txtout.sendPlayerMessageInfo( p.getName() + "`s leftover time: " + minutesLeft + ".", p);
        return true;
    }
}
