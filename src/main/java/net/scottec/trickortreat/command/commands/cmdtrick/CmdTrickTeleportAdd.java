package net.scottec.trickortreat.command.commands.cmdtrick;

import net.scottec.trickortreat.command.CommandBase;
import net.scottec.trickortreat.TrickOrTreat;
import net.scottec.trickortreat.tricks.Teleport;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdTrickTeleportAdd extends CommandBase{

    public CmdTrickTeleportAdd(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "add");
        this.setCommandUsage("/tot trick teleport add : Add actual position as TeleportLocation");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            ((Teleport) this.plugin.getTrickHandler().getTrickByName("teleport"))
                    .addPortLocation(sender.getLocation());
            sender.sendMessage("[ToT] Teleport added");
        }
        else
            sender.sendMessage("No permission");
    }
}
