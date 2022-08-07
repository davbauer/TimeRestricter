package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class AvailableTimeInMinutesCommand implements CommandExecutor {

    private final String getKey = "availableTimeInMinutes";
    private final TimeRestricter main;

    public AvailableTimeInMinutesCommand(TimeRestricter main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // Don't allow normal players to change the availableTimeInMinutes
        if (sender instanceof Player) {
            if (!((Player) sender).isOp()) {
                return false;
            }
        }

        if (args.length > 0) {
            main.getConfig().set(this.getKey, args[0]);
            main.saveConfig();
            main.txtout.sendGlobalMessageInfo(this.getKey + " changed to: " + main.getConfig().getString(this.getKey));
        } else {
            String myMessage = this.getKey + " is set to: " + main.getConfig().getString(this.getKey);
            if (sender instanceof Player) {
                main.txtout.sendPlayerMessageInfo(myMessage, (Player) sender);
            } else if (sender instanceof ConsoleCommandSender) {
                Bukkit.getConsoleSender().sendMessage(myMessage);
            }
        }
        return true;
    }
}
