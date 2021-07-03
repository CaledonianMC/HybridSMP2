package me.caledonian.hybridsmpv2.managers;

import me.caledonian.hybridsmpv2.commands.developer.CalItem;
import me.caledonian.hybridsmpv2.commands.developer.Pack;
import me.caledonian.hybridsmpv2.commands.gamemode.Adventure;
import me.caledonian.hybridsmpv2.commands.gamemode.Creative;
import me.caledonian.hybridsmpv2.commands.gamemode.Spectator;
import me.caledonian.hybridsmpv2.commands.gamemode.Survival;
import me.caledonian.hybridsmpv2.commands.patreon.Status;
import me.caledonian.hybridsmpv2.commands.player.Coords;
import me.caledonian.hybridsmpv2.commands.player.GlobalChat;
import me.caledonian.hybridsmpv2.commands.player.List;
import me.caledonian.hybridsmpv2.commands.reactions.*;
import me.caledonian.hybridsmpv2.commands.staff.*;
import me.caledonian.hybridsmpv2.commands.player.StaffList;
import me.caledonian.hybridsmpv2.listener.ChatFormatting;
import me.caledonian.hybridsmpv2.listener.CustomTextures;
import me.caledonian.hybridsmpv2.listener.PlayerJoin;
import me.caledonian.hybridsmpv2.utils.Logger;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {
    private Map<String, CommandHandler> commands = new HashMap<>();
    private JavaPlugin javaPlugin;
    private JDA bot;
    public CommandManager(JavaPlugin javaPlugin, JDA bot){
        this.javaPlugin = javaPlugin;
        this.bot = bot;
        initCommands();
        registerEvents();
    }
    private void initCommands(){
        // Core

        // Staff
        commands.put("vanish", new Vanish(javaPlugin, bot));
        commands.put("staff", new StaffList());
        commands.put("chat", new Chat());
        commands.put("staffchat", new StaffChat(javaPlugin, bot));
        commands.put("testkb", new TestKB(javaPlugin, bot));
        commands.put("demotroll", new DemoTroll());

        // Gamemodes
        commands.put("gmc", new Creative());
        commands.put("gms", new Survival());
        commands.put("gma", new Adventure());
        commands.put("gmsp", new Spectator());
        // Reactions
        commands.put("bruh", new Bruh());
        commands.put("burn", new BurntKetchup());
        commands.put("doubt", new Doubt());
        commands.put("epic", new EpicGamerMoment());
        commands.put("f", new F());
        commands.put("gg", new GoodGame());
        commands.put("nf", new NoF());
        commands.put("rip", new Rip());
        commands.put("sleep", new Sleep());
        commands.put("swamp", new Swamp());
        commands.put("ugly", new Ugly());

        // Player
        commands.put("list", new List());
        commands.put("globalchat", new GlobalChat());
        commands.put("coords", new Coords());

        // Pateron
        commands.put("status", new Status());

        registerCommands();
    }
    private void registerCommands() {Logger.log(Logger.LogLevel.INFO, "HybridSMP will now register commands..."); commands.forEach((s, c) -> javaPlugin.getCommand(s).setExecutor(this));}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmdname = command.getName();
        CommandHandler commandHandler = commands.get(cmdname);
        if(commandHandler != null) commandHandler.execute(sender, command,args);
        return false;
    }

    public void registerEvents(){
        Bukkit.getServer().getPluginManager().registerEvents(new ChatFormatting(javaPlugin, bot), javaPlugin);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(javaPlugin, bot), javaPlugin);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomTextures(), javaPlugin);
    }
}
