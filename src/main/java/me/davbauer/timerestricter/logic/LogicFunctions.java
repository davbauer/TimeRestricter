package me.davbauer.timerestricter.logic;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class LogicFunctions {
    final TimeRestricter main;
    public LogicFunctions(TimeRestricter main) {
        this.main = main;
    }



    public boolean senderGotRights(String permissionStr, CommandSender sender) {
        // Leave permissionStr just empty if not wanted to use

        boolean opPlayersAllowed = main.getConfig().getBoolean("opPlayersHaveFullRights");

        if (sender instanceof ConsoleCommandSender) return true;

        Player p = (Player) sender;

        if(opPlayersAllowed) {

            if (permissionStr.length() > 0) {
                if (p.isOp() || p.hasPermission(permissionStr)) return true;
            } else {
                if (p.isOp()) return true;
            }

        } else {

            if (permissionStr.length() > 0) {;
                if (p.hasPermission(permissionStr) && !p.isOp()) return true;
            }

        }


        main.txtout.sendPlayerMessageWarn("Sorry, but you dont have sufficient rights. (" + permissionStr + ").", p);
        return false;
    }

    public boolean senderAllowedBasicCommands() {
        return main.getConfig().getBoolean("allowBasicCommandsWithoutPermissions");
    }

    public boolean pluginIsEnabled() {
        return main.getConfig().getBoolean("enabled");
    }

    public boolean pluginIsEnabledWithMsg(CommandSender sender) {
        boolean isEnabled = this.pluginIsEnabled();

        if (isEnabled) return true;

        this.sendMsgToCorrectSenderWarn("Plugin is disabled, use `/tr-enabled true` to enable it again.", sender);

        return false;
    }

    public void sendToConsole(String message) {
        Bukkit.getConsoleSender().sendMessage("[TimeRestricter]: " + message);
    }

    public void sendMsgToCorrectSender(String outputmsg, CommandSender sender) {
        if (sender instanceof Player) {
            main.txtout.sendPlayerMessage(outputmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            this.sendToConsole(outputmsg);
        }
    }

    public void sendMsgToCorrectSenderInfo(String outputmsg, CommandSender sender) {
        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(outputmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            this.sendToConsole(outputmsg);
        }
    }

    public void sendMsgToCorrectSenderWarn(String outputmsg, CommandSender sender) {
        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageWarn(outputmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            this.sendToConsole(outputmsg);
        }
    }

    public boolean dataForPlayerCreated(String datapath) {
        String result = main.getConfig().getString(datapath);
        if (result == null) return false;
        return true;
    }

    public String generateKickMessage() {
        String configFillUpHour = main.getConfig().getString("fillUpTimeHour");
        String configFillUpMinute = main.getConfig().getString("fillUpTimeMinute");
        if (configFillUpHour.length() == 1) configFillUpHour = "0" + configFillUpHour;
        if (configFillUpMinute.length() == 1) configFillUpMinute = "0" + configFillUpMinute;

        String configFillUpTime = configFillUpHour + ":" + configFillUpMinute;
        String msg = "[TimeRestricter]: " + ChatColor.RED + "Your time is up! " + ChatColor.AQUA + " Time refill at " + ChatColor.BLUE + configFillUpTime;
        return msg;
    }

}
