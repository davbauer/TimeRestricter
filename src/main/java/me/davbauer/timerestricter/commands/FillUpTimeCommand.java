package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import me.davbauer.timerestricter.logic.LogicFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class FillUpTimeCommand implements CommandExecutor {

    private final TimeRestricter main;
    private final LogicFunctions lf;

    public FillUpTimeCommand(TimeRestricter main) {
        this.main = main;
        this.lf = new LogicFunctions(main);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        String outmsg = "";
        if (args.length == 0) {
            if(!lf.senderAllowedBasicCommands()) {
                if (!lf.senderGotRights("timerestricter.view_filluptime", sender)) return false;
            }
            String getHour = main.getConfig().getString("fillUpTimeHour");
            String getMinutes = main.getConfig().getString("fillUpTimeMinute");
            
            String daystr = "";
            List<Integer> getDays = main.getConfig().getIntegerList("fillUpTimeDays");
            for (final int day : getDays) {
                daystr = daystr.concat(Integer.toString(day));
            }
            
            outmsg = "Current fillUpTime-Config: " + getHour + ":" + getMinutes + " [" + daystr + "].";
        } else if (args.length > 0) {
            if (!lf.senderGotRights("timerestricter.change_filluptime", sender)) return false;

            String[] inputTime = args[0].split(":");


            if (inputTime.length != 2) return false;


            int newConfigHour;
            int newConfigMinute;

            try {
                newConfigHour = Integer.parseInt(inputTime[0]);
                newConfigMinute = Integer.parseInt(inputTime[1]);

                if (newConfigHour > 24 || newConfigMinute > 60) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            main.getConfig().set("fillUpTimeHour", newConfigHour);
            main.getConfig().set("fillUpTimeMinute", newConfigMinute);


            if (args.length > 1) {
                List<Integer> daysArray = new ArrayList<Integer>();
                String inputDaysStr = args[1];

                for (final char x : inputDaysStr.toCharArray()) {
                    String one = Character.toString(x);
                    int intval;
                    try {
                        intval = Integer.parseInt(one);
                        daysArray.add(intval);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    if (intval > 7 || intval < 1) {
                        return false;
                    }
                }
                if (daysArray.size() > 7) return false;

                main.getConfig().set("fillUpTimeDays", daysArray);
                outmsg = "Changed fillUpTime-Config: " + inputTime[0] + ":" + inputTime[1] + " [" + inputDaysStr + "].";
            } else {
                String daystr = "";
                List<Integer> getDays = main.getConfig().getIntegerList("fillUpTimeDays");
                for (final int day : getDays) {
                    daystr = daystr.concat(Integer.toString(day));
                }

                outmsg = "Changed fillUpTime-Config: " + inputTime[0] + ":" + inputTime[1] + " [" + daystr + "].";
            }

            main.saveConfig();

        } else {
            return false;
        }

        if (sender instanceof Player) {
            if (args.length > 0) {
                main.txtout.sendGlobalMessageInfo(outmsg);
            } else {
                main.txtout.sendPlayerMessageInfo(outmsg, (Player) sender);
            }

        } else if (sender instanceof ConsoleCommandSender) {
            lf.sendToConsole(outmsg);
        }
        return true;
    }
}
