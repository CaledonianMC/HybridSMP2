package me.caledonian.hybridsmpv2.utils;

import me.caledonian.hybridsmpv2.HybridSMPv2;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Files {

    public static File configFile;
    public static FileConfiguration config;

    public static File msgsFile;
    public static FileConfiguration msgs;

    public static File permFile;
    public static FileConfiguration perms;

    public static File discordFile;
    public static FileConfiguration discord;

    public static File wordFile;
    public static FileConfiguration word;

    public static void base(HybridSMPv2 m) {
        if (!m.getDataFolder().exists()) {
            m.getDataFolder().mkdirs();
        }
        // config.yml
        configFile = new File(m.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File config.yml doesn't exist, creating one...");
            m.saveResource("config.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File config.yml was created.");
        }
        // msgs.yml
        msgsFile = new File(m.getDataFolder(), "msgs.yml");
        if (!msgsFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File msgs.yml doesn't exist, creating one...");
            m.saveResource("msgs.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File msgs.yml was created.");
        }
        // perms.yml
        permFile = new File(m.getDataFolder(), "perms.yml");
        if (!permFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File perms.yml doesn't exist, creating one...");
            m.saveResource("perms.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File perms.yml was created.");
        }
        // discord.yml
        discordFile = new File(m.getDataFolder(), "discord.yml");
        if (!discordFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File discord.yml doesn't exist, creating one...");
            m.saveResource("discord.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File discord.yml was created.");
        }
        // words.yml
        wordFile = new File(m.getDataFolder(), "words.yml");
        if (!wordFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File words.yml doesn't exist, creating one...");
            m.saveResource("words.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File words.yml was created.");
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        Logger.log(Logger.LogLevel.INFO, "[Files] File config.yml was loaded");
        msgs = YamlConfiguration.loadConfiguration(msgsFile);
        Logger.log(Logger.LogLevel.INFO, "[Files] File msgs.yml was loaded");
        perms = YamlConfiguration.loadConfiguration(permFile);
        Logger.log(Logger.LogLevel.INFO, "[Files] File perms.yml was loaded");
        discord = YamlConfiguration.loadConfiguration(discordFile);
        Logger.log(Logger.LogLevel.INFO, "[Files] File discord.yml was loaded");
        word = YamlConfiguration.loadConfiguration(wordFile);
        Logger.log(Logger.LogLevel.INFO, "[Files] File words.yml was loaded");
    }
}
