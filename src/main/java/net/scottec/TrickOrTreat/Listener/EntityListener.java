package net.scottec.TrickOrTreat.Listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Fabian on 30.10.2015.
 */
public class EntityListener implements Listener
{
    public EntityListener(JavaPlugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}
