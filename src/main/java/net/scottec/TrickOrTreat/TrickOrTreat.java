package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Listener.EntityListener;
import net.scottec.TrickOrTreat.Listener.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Fabian on 16.10.2015.
 */
public class TrickOrTreat extends JavaPlugin
{
    //private static TrickOrTreat plugin;

    private MySQL oMySQL;
    private RequestHandler oRequestHandler;
    private Trick oTrick;
    private Treat oTreat;
    private Ghost oGhost;

    @Override
    public void onEnable()
    {
        // load config files
        Config.reloadConfig(this);
        //plugin = this;
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
        oRequestHandler = new RequestHandler(this, new ToTAdapter());
        oGhost = new Ghost(this, new ToTAdapter());
        oTrick = new Trick(this, new ToTAdapter());
        oTreat = new Treat(this);
        new PlayerListener(this, new ToTAdapter());
        new EntityListener(this, new ToTAdapter());
    }

    private class ToTAdapter implements ToTFace
    {
        @Override
        public MySQL getMySQL(){
            return oMySQL;
        }

        @Override
        public RequestHandler getRequestHandler(){
            return oRequestHandler;
        }

        @Override
        public Trick getTrick(){
            return oTrick;
        }

        @Override
        public Treat getTreat(){
            return oTreat;
        }

        @Override
        public Ghost getGhost(){
            return oGhost;
        }
    }

    public interface ToTFace
    {
        MySQL getMySQL();
        RequestHandler getRequestHandler();
        Trick getTrick();
        Treat getTreat();
        Ghost getGhost();
    }
}
