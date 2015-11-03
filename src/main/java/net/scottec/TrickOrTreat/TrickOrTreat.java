package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Listener.EntityListener;
import net.scottec.TrickOrTreat.Listener.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Fabian on 16.10.2015.
 */
public class TrickOrTreat extends JavaPlugin
{
    public static MySQL oMySQL;
    public static RequestHandler oRequestHandler;
    public static Trick oTrick;
    public static Treat oTreat;
    public static Ghost oGhost;
    public static JavaPlugin plugin;

    @Override
    public void onEnable()
    {
        // load config files
        Config.reloadConfig(this);
        this.plugin = this;
        createObjects();
    }

    @Override
    public void onDisable()
    {
        oGhost.killAllGhosts();
    }

    private void createObjects()
    {
        oMySQL = new MySQL(this);
        oRequestHandler = new RequestHandler(this);
        oGhost = new Ghost(this);
        oTrick = new Trick(this);
        oTreat = new Treat(this);
        new PlayerListener(this);
        new EntityListener(this);
    }
}
