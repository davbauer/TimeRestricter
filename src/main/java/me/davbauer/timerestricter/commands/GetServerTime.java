package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetServerTime implements CommandExecutor {

    private final TimeRestricter main;

    public GetServerTime(TimeRestricter main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String outputmsg = "Server time: " + dtf.format(now);

        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(outputmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("[TimeRestricter]: " + outputmsg);
        }

        return true;
    }
}
