package me.caledonian.hybridsmpv2.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String format(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String chat(String msg) {
        final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        if (Bukkit.getVersion().contains("1.16")) {
            Matcher match = pattern.matcher(msg);
            while (match.find()) {
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(msg);
            }
        }
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void sendLog(String msg, String perm, Player sender){
        for(Player o : Bukkit.getOnlinePlayers()){
            if(o.getName() != sender.getName()){
                if(o.hasPermission(perm)){
                    o.sendMessage(chat(msg).replace("%prefix%", Utils.chat(Files.msgs.getString("prefix"))).replace("%player%", sender.getName()));
                }
            }
        }
    }
    public static void broadcast(String message){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(message);
        }
    }

    public static String censorWords(String... words){
        String re = "";
        for(String w : words)
            for(int i = 0; i < w.length(); i++)
                re += String.format("|((?<=%s)%s(?=%s))",
                        w.substring(0, i), w.charAt(i), w.substring(i + 1));
            return re.substring(1);
    }

    public static String censorWords(String message){

        List<String> blocked = Files.word.getStringList("blocked");
        String finalmsg;

            for(String bWord : blocked){
                if(message.contains(bWord)){
                    bWord.replaceAll("\\.", "*");
                }
            }
            return message;
    }



    // Other Strings
    public static String ver = "0.1.2";
}