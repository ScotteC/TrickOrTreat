package net.scottec.TrickOrTreat;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.scottec.TrickOrTreat.Command.CommandHandler;
import net.scottec.TrickOrTreat.Command.commands.CmdTrickOrTreat;
import net.scottec.TrickOrTreat.Command.commands.CmdTrickTeleport;
import net.scottec.TrickOrTreat.Listener.EntityListener;
import net.scottec.TrickOrTreat.Listener.ItemListeners;
import net.scottec.TrickOrTreat.Listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TrickOrTreat extends JavaPlugin {
    private TrickOrTreat plugin;
    private Database oDatabase;
    private RequestHandler oRequestHandler;
    private TreatHandler oTreatHandler;
    private TrickHandler oTrickHandler;
    private Ghost oGhost;
//    private CSMessageHandler oCSMessageHandler;

    private ITrickOrTreat iTrickOrTreat = new ATrickOrTreat();

    @Override
    public void onEnable() {
        this.plugin = this;
        // load config files
        Config.reloadConfig(this);

        // create objects
        this.oDatabase = new Database(this.iTrickOrTreat);
        this.oRequestHandler = new RequestHandler(this.iTrickOrTreat);
        this.oTreatHandler = new TreatHandler(this.iTrickOrTreat);
        this.oTrickHandler = new TrickHandler(this.iTrickOrTreat);
        this.oGhost = new Ghost(this.iTrickOrTreat);
        new PlayerListener(this.iTrickOrTreat);
        new EntityListener(this.iTrickOrTreat);
        new ItemListeners(this.iTrickOrTreat);

        CommandHandler.init(this.iTrickOrTreat);
        CommandHandler.instance.addCommand(new CmdTrickOrTreat(this.iTrickOrTreat));

//        this.oCSMessageHandler = new CSMessageHandler();
//        this.oCSMessageHandler = null;
    }

    @Override
    public void onDisable() {
        this.oGhost.killAllGhosts();
    }

    public interface ITrickOrTreat {
        TrickOrTreat getPlugin();

        Database getDatabase();

        RequestHandler getRequestHandler();

        TreatHandler getTreatHandler();

        TrickHandler getTrickHandler();

        Ghost getGhost();

        WorldGuardPlugin getWorldGuard();

//        CSMessageHandler getCSMessageHandler();
    }

    private class ATrickOrTreat implements ITrickOrTreat {
        @Override
        public TrickOrTreat getPlugin() {
            return plugin;
        }

        @Override
        public Database getDatabase() {
            return oDatabase;
        }

        @Override
        public RequestHandler getRequestHandler() {
            return oRequestHandler;
        }

        @Override
        public TreatHandler getTreatHandler() {
            return oTreatHandler;
        }

        @Override
        public TrickHandler getTrickHandler() {
            return oTrickHandler;
        }

        @Override
        public Ghost getGhost() {
            return oGhost;
        }

        @Override
        public WorldGuardPlugin getWorldGuard() {
            return WGBukkit.getPlugin();
        }

//        @Override
//        public CSMessageHandler getCSMessageHandler() {
//            return oCSMessageHandler;
//        }
    }
}
