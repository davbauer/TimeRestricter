package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AvailableMinutesCommand implements CommandExecutor {

    private final TimeRestricter main;

    public AvailableMinutesCommand(TimeRestricter main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String outmsg = "";

        if (args.length == 0) {
            String configAvailableMinutes = main.getConfig().getString("availableMinutes");
            outmsg = "Current availableMinutes-Config: " + configAvailableMinutes + "m.";
        } else {
            String inputMinutesStr = args[0];
            int inputMinutes = 0;

            try {
                inputMinutes = Integer.parseInt(inputMinutesStr);
            } catch (NumberFormatException e) {
                return false;
            }

            if (inputMinutes < 1) {
                return false;
            }

            main.getConfig().set("availableMinutes", inputMinutes);
            main.saveConfig();
            outmsg = "Changed availableMinutes-Config: " + inputMinutes + "m.";

            // After changing the available Time, the rememberList for notifications should be cleared for new notifications
            main.checkTimeOnPlayers.cleanRememberLists();
        }

        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(outmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("[TimeRestricter]: " + outmsg);
        }
        return true;
    }
}
