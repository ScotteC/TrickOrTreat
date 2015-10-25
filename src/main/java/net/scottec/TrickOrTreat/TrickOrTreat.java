package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Ghosts.Ghost;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Fabian on 16.10.2015.
 */
public class TrickOrTreat extends JavaPlugin implements Listener
{

    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(new RequestHandler(this), this);
        this.getServer().getPluginManager().registerEvents(new Ghost(), this);
        this.getServer().getPluginManager().registerEvents(new Trick(), this);
        this.getServer().getPluginManager().registerEvents(new Treat(), this);

//        loadConfig();
    }

//    private void loadConfig()
//    {
//        this.requestTimeout = 5;      // seconds
//        this.requestCooldown = 15;    // seconds
//    }
}
