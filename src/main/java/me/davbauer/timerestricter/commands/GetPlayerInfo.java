package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class GetPlayerInfo implements CommandExecutor {

    private final TimeRestricter main;

    public GetPlayerInfo(TimeRestricter main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if(sender instanceof Player) {
                return sendResponse(((Player) sender).getPlayer());
            }

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
        String minutesLeft = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(calculatedMillis),
                TimeUnit.MILLISECONDS.toSeconds(calculatedMillis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(calculatedMillis))
        );

        main.txtout.sendPlayerMessageInfo( p.getName() + "`s leftover time: " + minutesLeft + ".", p);
        return true;
    }
}
