package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreditsInfoCommand implements CommandExecutor {


    private final TimeRestricter main;

    public CreditsInfoCommand(TimeRestricter main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String outputmsg = ChatColor.GOLD + "\nMade by github.com/davbauer.\nMade with " + ChatColor.RED + " ❤" + ChatColor.GOLD + ".";
        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(outputmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("[TimeRestricter]: " + outputmsg);
        }

        return true;
    }
}
