package me.davbauer.timerestricter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TextOut {

    static String outputTextPrefix = ChatColor.WHITE + "[TimeRestricter]: ";

    public void Functions() {

    }

    public void sendPlayerMessage(String msg, Player p) {
        p.sendMessage(outputTextPrefix + ChatColor.WHITE+msg);
    }

    public void sendPlayerMessageInfo(String msg, Player p) {
        this.sendPlayerMessage(ChatColor.YELLOW + msg, p);
    }

    public void sendPlayerMessageWarn(String msg, Player p) {
        this.sendPlayerMessage(ChatColor.RED + msg, p);
    }

    public void sendGlobalMessage(String msg) {
        String myMessage = outputTextPrefix + ChatColor.WHITE+msg;
        Bukkit.getConsoleSender().sendMessage(myMessage);
        for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(myMessage);
        }
    }

    public void sendGlobalMessageInfo(String msg) {
        this.sendGlobalMessage(ChatColor.YELLOW + msg);
    }

    public void sendGlobalMessageWarn(String msg) {
        this.sendGlobalMessage(ChatColor.RED + msg);
    }


}
