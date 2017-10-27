package net.scottec.trickortreat.command;

import net.scottec.trickortreat.TrickOrTreat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public class CommandHandler implements Listener, CommandExecutor {
    public static CommandHandler instance;

    protected TrickOrTreat.ITrickOrTreat plugin;
    protected Map<String, ICommand> commands;

    public static void init(TrickOrTreat.ITrickOrTreat plugin) {
        if (instance == null)
            instance = new CommandHandler(plugin);
    }

    private CommandHandler(TrickOrTreat.ITrickOrTreat plugin) {
        this.plugin = plugin;
        this.commands = new HashMap<>();
        this.plugin.getPlugin().getServer().getPluginManager().registerEvents(this, plugin.getPlugin());
    }

    public void addCommand(ICommand command) {
        for (String commandRoot : command.getAliases()) {
            this.commands.put(commandRoot.toLowerCase(), command);
        }
        this.plugin.getPlugin().getCommand(command.getCommandName()).setExecutor(this);
        command.setCommandHandler(this);
    }

    public void removeCommand(ICommand command) {
        for (String commandRoot : command.getAliases()) {
            commands.remove(commandRoot.toLowerCase(), command);
            command.setCommandHandler(null);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            ICommand command = commands.get(cmd.getName().toLowerCase());

            if (command != null) {
                command.execute(player, Arrays.asList(args));
                return true;
            }
        }
        return false;
    }

}
