package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GetPlayerInfo implements CommandExecutor {

    private final TimeRestricter main;

    public GetPlayerInfo(TimeRestricter main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String searchPlayerName;
        if (args.length != 0) {
            searchPlayerName = args[0];
        } else {
            if (! (sender instanceof Player)) { return false; };
            Player senderPlayer = (Player) sender;
            searchPlayerName = senderPlayer.getName();
        }
        String message = "";

        List<String> existingList = (List<String>) main.getConfig().getList("livePlayerTime");
        for (final String x : existingList) {
            final String[] split = x.split(";");
            final String configPlayerName = split[0];

            if (configPlayerName.equals(searchPlayerName)) {
                final double configPlayerTime = Double.parseDouble(split[2]);

                message = configPlayerTime/60 + "m remaining for " + searchPlayerName;

                break;
            }
        }

        if (message.length() == 0) {
            message = "Player could not be found.";
        }

        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(message, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage(message);
        }

        return true;
    }
}
