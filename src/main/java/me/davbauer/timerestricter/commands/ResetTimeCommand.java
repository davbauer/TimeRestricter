package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetTimeCommand implements CommandExecutor {

    private final TimeRestricter main;

    public ResetTimeCommand(TimeRestricter main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        main.resetTimeForPlayers.doRoutine();

        return true;
    }
}
