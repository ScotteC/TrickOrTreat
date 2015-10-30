package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Ghosts.Ghost;
import net.scottec.TrickOrTreat.Listener.EntityListener;
import net.scottec.TrickOrTreat.Listener.PlayerListener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Fabian on 16.10.2015.
 */
public class TrickOrTreat extends JavaPlugin
{
    public static RequestHandler oRequestHandler;
    public static Trick oTrick;
    public static Treat oTreat;
    public static Ghost oGhost;

    @Override
    public void onEnable()
    {
        // load config files
        Config.reloadConfig(this);

        // register EventListener
        createObjects();
    }

    @Override
    public void onDisable()
    {
        oGhost.killAllGhosts();
    }

    private void createObjects()
    {
        oRequestHandler = new RequestHandler(this);
        oGhost = new Ghost(this);
        oTrick = new Trick(this);
        oTreat = new Treat(this);
        new PlayerListener(this);
        new EntityListener(this);

//        registerEvent(new RequestHandler(this));
//        registerEvent(new Ghost(this));
//        registerEvent(new Trick());
//        registerEvent(new Treat());
//        registerEvent(new PlayerListener());
    }

//    private void registerEvent(Listener listener)
//    {
//        this.
//        this.getServer().getPluginManager().registerEvents(listener,this);
//    }
}
