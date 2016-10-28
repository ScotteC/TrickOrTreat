package net.scottec.TrickOrTreat;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.scottec.TrickOrTreat.Command.CommandHandler;
import net.scottec.TrickOrTreat.Command.commands.CmdTrickOrTreat;
import net.scottec.TrickOrTreat.Handler.*;
import net.scottec.TrickOrTreat.Command.commands.CmdTrickTeleport;
import net.scottec.TrickOrTreat.Listener.EntityListener;
import net.scottec.TrickOrTreat.Listener.ItemListeners;
import net.scottec.TrickOrTreat.Listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TrickOrTreat extends JavaPlugin {
    private TrickOrTreat plugin;
    private CSVoteHandler oCSVoteHandler;
    private RequestHandler oRequestHandler;
    private TreatHandler oTreatHandler;
    private TrickHandler oTrickHandler;
    private GhostHandler oGhostHandler;
    private CSMessageHandler oCSMessageHandler;

    private ITrickOrTreat iTrickOrTreat = new ATrickOrTreat();

    @Override
    public void onEnable() {
        this.plugin = this;
        // load config files
        Config.reloadConfig(this);

        // create objects
        this.oCSVoteHandler = new CSVoteHandler();
        this.oRequestHandler = new RequestHandler(this.iTrickOrTreat);
        this.oTreatHandler = new TreatHandler(this.iTrickOrTreat);
        this.oTrickHandler = new TrickHandler(this.iTrickOrTreat);
        this.oGhostHandler = new GhostHandler(this.iTrickOrTreat);
        new PlayerListener(this.iTrickOrTreat);
        new EntityListener(this.iTrickOrTreat);
        new ItemListeners(this.iTrickOrTreat);

        CommandHandler.init(this.iTrickOrTreat);
        CommandHandler.instance.addCommand(new CmdTrickOrTreat(this.iTrickOrTreat));

        this.oCSMessageHandler = new CSMessageHandler();
    }

    @Override
    public void onDisable() {
        this.oGhostHandler.killAllGhosts();
        util.clearRessources();
    }

    public interface ITrickOrTreat {
        TrickOrTreat getPlugin();

        CSVoteHandler getCSVoteHandler();

        RequestHandler getRequestHandler();

        TreatHandler getTreatHandler();

        TrickHandler getTrickHandler();

        GhostHandler getGhostHandler();

        WorldGuardPlugin getWorldGuard();

        CSMessageHandler getCSMessageHandler();
    }

    private class ATrickOrTreat implements ITrickOrTreat {
        @Override
        public TrickOrTreat getPlugin() {
            return plugin;
        }

        @Override
        public CSVoteHandler getCSVoteHandler() {
            return oCSVoteHandler;
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
        public GhostHandler getGhostHandler() {
            return oGhostHandler;
        }

        @Override
        public WorldGuardPlugin getWorldGuard() {
            return WGBukkit.getPlugin();
        }

        @Override
        public CSMessageHandler getCSMessageHandler() {
            return oCSMessageHandler;
        }
    }
}
