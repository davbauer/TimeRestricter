package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreditsInfoCommand implements CommandExecutor {


    private final TimeRestricter main;
    private final LogicFunctions lf;

    public CreditsInfoCommand(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String outputmsg = ChatColor.GOLD + "\nMade by github.com/davbauer.\nMade with " + ChatColor.RED + " ❤" + ChatColor.GOLD + ".";

        lf.sendMsgToCorrectSender(outputmsg, sender);

        return true;
    }
}
