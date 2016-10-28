package net.scottec.TrickOrTreat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private static FileConfiguration cfg;
    private static File cfgFile;

    public static void reloadConfig(TrickOrTreat plugin) {
        cfgFile = new File(plugin.getDataFolder(), "config.yml");

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

    public static void saveCfg() {
        try {
            cfg.save(cfgFile);
        }
        catch (IOException exp) {
            System.out.println("[TrickOrTret] Saving configuration failed");
        }
    }
}
