package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdTrickTeleportAdd extends CommandBase{

    public CmdTrickTeleportAdd(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "add");
        this.setCommandUsage("/tot trick teleport add");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            //TODO: add new teleportlocation to trick Teleport
        }
        else
            sender.sendMessage("No permission");
    }
}