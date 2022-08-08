package me.davbauer.timerestricter.commands;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SetFillUpTime implements CommandExecutor {

    private final TimeRestricter main;

    public SetFillUpTime(TimeRestricter main) {
        this.main = main;
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        String outmsg = "";
        if (args.length == 0) {
            String getHour = main.getConfig().getString("fillUpTimeHour");
            String getMinutes = main.getConfig().getString("fillUpTimeMinute");
            
            String daystr = "";
            List<Integer> getDays = main.getConfig().getIntegerList("fillUpTimeDays");
            for (final int day : getDays) {
                daystr = daystr.concat(Integer.toString(day));
            }
            
            outmsg = "Current fillUpTime-Config: " + getHour + ":" + getMinutes + " [" + daystr + "].";
        } else if (args.length == 2) {
            String[] inputTime = args[0].split(":");
            String inputDaysStr = args[1];

            if (inputTime.length != 2) return false;

            List<Integer> daysArray = new ArrayList<Integer>();


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
            main.getConfig().set("fillUpTimeDays", daysArray);
            main.saveConfig();


            outmsg = "Changed fillUpTime-Config: " + inputTime[0] + ":" + inputTime[1] + " [" + inputDaysStr + "].";

        } else {
            return false;
        }

        if (sender instanceof Player) {
            main.txtout.sendPlayerMessageInfo(outmsg, (Player) sender);
        } else if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("[TimeRestricter]: " + outmsg);
        }
        return true;
    }
}
