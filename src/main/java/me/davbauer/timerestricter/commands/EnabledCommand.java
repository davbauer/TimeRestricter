package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class EnabledCommand implements CommandExecutor {

    private final TimeRestricter main;
    private final LogicFunctions lf;

    public EnabledCommand(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean configEnabled = main.getConfig().getBoolean("enabled");

        if (args.length > 0) {
            if (!lf.senderGotRights("timerestricter.change_plugin_enabled", sender)) return false;

            boolean inputBool = Boolean.parseBoolean(args[0]);
            main.getConfig().set("enabled", inputBool);
            main.saveConfig();
            main.txtout.sendGlobalMessageInfo("Changed enabled-config: " + inputBool + ".");

            if (inputBool == true) {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    main.onPlayerLoginEvent.PlayerOnline(p);
                }
            } else {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    main.onPlayerQuitEvent.PlayerOffline(p);
                }
            }

        } else {
            if(!lf.senderAllowedBasicCommands()) {
                if (!lf.senderGotRights("timerestricter.view_plugin_enabled", sender)) return false;
            }
            lf.sendMsgToCorrectSenderInfo("Current enabled-config: " + configEnabled + ".", sender);
        }



        return true;
    }
}
