package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PlayersTimeCommand implements CommandExecutor {

    private final TimeRestricter main;

    public PlayersTimeCommand(TimeRestricter main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String outputmsg = "Remaining time of known players for today:\n";
        long configTime = main.getConfig().getLong("availableMinutes") * 60000; // Convert Minutes in Config to Millis
        long currentMillis = System.currentTimeMillis();
        for (final String uuid : main.getConfig().getConfigurationSection("data").getKeys(false)) {
            String playerPath = "data." + uuid;
            String playerName = main.getConfig().getString(playerPath + ".name");
            long joinedMillis = main.getConfig().getLong(playerPath + ".joined");
            long spentTime = main.getConfig().getLong(playerPath + ".spent");

            long calculatedMillis = configTime - spentTime - (currentMillis - joinedMillis);
            String minutesLeft = String.format("%dm, %ds",
                    TimeUnit.MILLISECONDS.toMinutes(calculatedMillis),
                    TimeUnit.MILLISECONDS.toSeconds(calculatedMillis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(calculatedMillis))
            );
            if (calculatedMillis < 0) {
                outputmsg = outputmsg.concat(  ChatColor.RESET +""+ ChatColor.WHITE+ "- " + playerName + " -> " + ChatColor.ITALIC + "" + ChatColor.RED + minutesLeft + "\n");
            } else {
                outputmsg = outputmsg.concat(  ChatColor.RESET +""+ ChatColor.WHITE+ "- " + playerName + " -> " + ChatColor.ITALIC + "" + ChatColor.GREEN + minutesLeft + "\n");
            }


        }

        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(outputmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("[TimeRestricter]: " + outputmsg);
        }

        return true;
    }
}
