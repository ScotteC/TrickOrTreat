package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Ghosts.Ghost;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Fabian on 16.10.2015.
 */
public class TrickOrTreat extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        // load config files
        Config.reloadConfig(this);

        // register EventListener
        registerListeners();
    }

    @Override
    public void onDisable()
    {
        Ghost.killAllGhosts();
    }

    private void registerListeners()
    {
        registerEvent(new RequestHandler(this));
        registerEvent(new Ghost(this));
        registerEvent(new Trick());
        registerEvent(new Treat());
    }

    private void registerEvent(Listener listener)
    {
        this.getServer().getPluginManager().registerEvents(listener,this);
    }
}
