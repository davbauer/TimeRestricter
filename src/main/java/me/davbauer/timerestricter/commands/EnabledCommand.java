package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class EnabledCommand implements CommandExecutor {

    private final TimeRestricter main;

    public EnabledCommand(TimeRestricter main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String outputmsg = "";
        boolean configEnabled = main.getConfig().getBoolean("enabled");

        if (args.length > 0) {
            boolean inputBool = Boolean.parseBoolean(args[0]);
            main.getConfig().set("enabled", inputBool);
            main.saveConfig();
            outputmsg = "Changed enabled-config: " + inputBool;

            if (configEnabled == inputBool) return true;
            Bukkit.getServer().reload();
        } else {
            outputmsg = "Current enabled-config: " + configEnabled;
        }

        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(outputmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("[TimeRestricter]: " + outputmsg);
        }

        return true;
    }
}
