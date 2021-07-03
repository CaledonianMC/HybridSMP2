package me.caledonian.hybridsmpv2.managers;

import me.caledonian.hybridsmpv2.commands.staff.StaffChat;
import me.caledonian.hybridsmpv2.commands.staff.Vanish;
import me.caledonian.hybridsmpv2.listener.ChatFormatting;
import me.caledonian.hybridsmpv2.utils.Data;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PlaceholderHandler extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "hybridcore";
    }

    @Override
    public String getAuthor() {
        return "Caledonian";
    }

    @Override
    public String getVersion() {
        return "2.0";
    }
    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if(p == null){
            return "ERROR: Null";
        }
        if (params.equals("nonvanished")){
            int num = 0;
            for(Player o : Bukkit.getOnlinePlayers()){
                if(!Vanish.vanished.contains(o)){
                    num = num + 1;
                }
            }
            return String.valueOf(num);
        }else if(params.equals("vanish_amount")){
            return String.valueOf(Vanish.vanished.size());
        }else if(params.equals("vanished")){
            if(Vanish.vanished.contains(p)){return ChatColor.GREEN + "Yes";}else{return ChatColor.RED + "No";}
        }else if(params.equals("staffchat")){
            if(StaffChat.sc.contains(p)){return ChatColor.GREEN + "Yes";}else{return ChatColor.RED + "No";}
        }else if(params.equals("chat_toggled")){
            if(Data.get().getBoolean("chat.toggle")){return ChatColor.RED + "Disabled";}else{return ChatColor.GREEN + "Enabled";}
        }else if(params.equals("status")){
            try{
                if(p.hasPermission(Files.perms.getString("patreon.status"))){return Utils.chat(Data.get().getString("players.%uuid%.status".replace("%uuid%", p.getUniqueId().toString())));}else{
                    return "";
                }
            }catch (Exception ignored){}
        }else if(params.equals("version_logo")){

            if(p.getName().startsWith("*")){
                return Files.config.getString("placeholders.version-logo.bedrock");
            }else{
                return Files.config.getString("placeholders.version-logo.java");
            }
        }else if(params.equals("version")){
            if(p.getName().startsWith("*")){
                return Files.config.getString("placeholders.version.bedrock");
            }else{
                return Files.config.getString("placeholders.version.java");
            }
        }
        return null;
    }
}

