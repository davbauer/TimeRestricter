package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetTimeCommand implements CommandExecutor {

    private final TimeRestricter main;
    private final LogicFunctions lf;

    public ResetTimeCommand(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!lf.senderGotRights("timerestricter.reset_time", sender)) return false;
        main.resetTimeForPlayers.doRoutine();
        return true;
    }
}
