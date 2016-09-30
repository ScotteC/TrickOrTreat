package net.scottec.TrickOrTreat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    private static FileConfiguration cfg;

    public static void reloadConfig(TrickOrTreat plugin) {
        File cfgFile = new File(plugin.getDataFolder(), "config.yml");

        if (cfgFile.isFile()) {
            try {
                cfg = YamlConfiguration.loadConfiguration(cfgFile);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } else {
            //load config from resource an save it
            cfg = plugin.getConfig();
            plugin.saveDefaultConfig();
        }
    }

    public static FileConfiguration getCfg() {
        return cfg;
    }
}
