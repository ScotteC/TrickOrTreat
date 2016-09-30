package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Items.Halloweenstick;
import net.scottec.TrickOrTreat.Listener.EntityListener;
import net.scottec.TrickOrTreat.Listener.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TrickOrTreat extends JavaPlugin {
    private TrickOrTreat plugin;
    private Database oDatabase;
    private RequestHandler oRequestHandler;
    private TreatHandler oTreatHandler;
    private TrickHandler oTrickHandler;
    private Ghost oGhost;
    private CSMessageHandler oCSMessageHandler;
    private Halloweenstick oHalloweenstick;

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
        this.oHalloweenstick = new Halloweenstick(this.iTrickOrTreat);
        //this.oCSMessageHandler = new CSMessageHandler();
        this.oCSMessageHandler = null;
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

        CSMessageHandler getCSMessageHandler();

        ItemStack getHalloweenstick();
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
        public CSMessageHandler getCSMessageHandler() {
            return oCSMessageHandler;
        }

        @Override
        public ItemStack getHalloweenstick() { return oHalloweenstick.getHalloweenstick(); }
    }
}
