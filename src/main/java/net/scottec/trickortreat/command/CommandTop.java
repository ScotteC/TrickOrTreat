package net.scottec.trickortreat.command;

import net.scottec.trickortreat.TrickOrTreat.ITrickOrTreat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandTop extends CommandBase implements ICommand {

    private Map<String, ICommand> subCommands;

    public CommandTop(ITrickOrTreat plugin, String... aliases) {
        super(plugin, aliases);
        this.subCommands = new HashMap<>();
    }

    protected void addSubCommand(ICommand command) {
        for (String commandRoot : command.getAliases()) {
            subCommands.put(commandRoot.toLowerCase(), command);
        }
    }

    protected void removeSubCommand(ICommand command) {
        for (String commandRoot : command.getAliases()) {
            subCommands.remove(commandRoot.toLowerCase(), command);
        }
    }

    @Override
    public void execute(Player player, List<String> args) {
        if (args != null && args.size() > 0) {
            ICommand command = subCommands.get(args.get(0).toLowerCase());

            if (command != null) {
                command.execute(player, args.subList(1, args.size()));
            }
            else {
                player.sendMessage("Usage:");
                for (Map.Entry<String, ICommand> entry : subCommands.entrySet()) {
                    player.sendMessage(entry.getValue().getCommandUsage());
                }
            }
        }
        else {
            player.sendMessage("Usage:");
            for (Map.Entry<String, ICommand> entry : subCommands.entrySet()) {
                player.sendMessage(entry.getValue().getCommandUsage());
            }
        }
    }
}
