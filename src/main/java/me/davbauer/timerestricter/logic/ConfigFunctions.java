package me.davbauer.timerestricter.logic;

import me.davbauer.timerestricter.TimeRestricter;
import org.bukkit.ChatColor;

public class ConfigFunctions {
    final TimeRestricter main;



    public ConfigFunctions(TimeRestricter main) {
        this.main = main;
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
