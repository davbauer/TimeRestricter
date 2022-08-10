package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AvailableMinutesCommand implements CommandExecutor {

    private final TimeRestricter main;
    private final LogicFunctions lf;

    public AvailableMinutesCommand(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String outputmsg = "";

        if (args.length == 0) {
            if(!lf.senderAllowedBasicCommands()) {
                if (!lf.senderGotRights("timerestricter.view_available_minutes", sender)) return false;
            }
            String configAvailableMinutes = main.getConfig().getString("availableMinutes");
            outputmsg = "Current availableMinutes-Config: " + configAvailableMinutes + "m.";
        } else {
            if (!lf.senderGotRights("timerestricter.change_available_minutes", sender)) return false;

            String inputMinutesStr = args[0];
            int inputMinutes = 0;

            try {
                inputMinutes = Integer.parseInt(inputMinutesStr);
            } catch (NumberFormatException e) {
                return false;
            }

            if (inputMinutes < 1) {
                return false;
            }

            main.getConfig().set("availableMinutes", inputMinutes);
            main.saveConfig();
            outputmsg = "Changed availableMinutes-Config: " + inputMinutes + "m.";

            // After changing the available Time, the rememberList for notifications should be cleared for new notifications
            main.checkTimeOnPlayers.cleanRememberLists();
        }

        lf.sendMsgToCorrectSenderInfo(outputmsg, sender);
        return true;
    }
}
