package net.scottec.TrickOrTreat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Fabian on 29.10.2015.
 */
public class Config
{
    private static FileConfiguration cfg;
    private static FileConfiguration txt;

    public static void reloadConfig(JavaPlugin plugin)
    {
        File cfgFile = new File(plugin.getDataFolder(), "config.yml");

        if( cfgFile.isFile() )
        {
            try
            {
                cfg = YamlConfiguration.loadConfiguration(cfgFile);
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
                return;
            }
        }
        else
        {
            //load config from resource an save it
            cfg = plugin.getConfig();
            plugin.saveDefaultConfig();
        }

        // reload language file specified in config
        String langFileName = cfg.getString("common.languagefile");

        File langFile = new File(plugin.getDataFolder(), langFileName);
        File defLangFile = new File(plugin.getDataFolder(), "en.yml");

        txt = YamlConfiguration.loadConfiguration(
                plugin.getResource("en.yml"));

        try
        {
            if(!defLangFile.exists())
            {
                defLangFile.createNewFile();
                txt.save(defLangFile);
            }

            if(!langFileName.equals("en.yml"))
            {
                if (!langFile.exists())
                {
                    langFile.createNewFile();
                    txt.save(langFile);
                }
                txt = YamlConfiguration.loadConfiguration(langFile);
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
            return;
        }
    }

    public static FileConfiguration getCfg(){return cfg;}
    public static FileConfiguration getTxt(){return txt;}
}
