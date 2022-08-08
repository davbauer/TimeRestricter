package me.davbauer.timerestricter.logic;

import me.davbauer.timerestricter.TimeRestricter;

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

}
