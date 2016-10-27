package net.scottec.TrickOrTreat.Command;

import net.scottec.TrickOrTreat.TrickOrTreat.ITrickOrTreat;

import java.util.Arrays;
import java.util.List;

public abstract class CommandBase implements ICommand {

    private String commandName;
    private String commandUsage = "";

    private List<String> aliases;

    protected CommandHandler commandHandler;
    protected ITrickOrTreat plugin;

    public CommandBase(ITrickOrTreat plugin, String... aliases) {
        this.plugin = plugin;
        this.aliases = Arrays.asList(aliases);
        this.commandName = this.aliases.get(0);
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public void setCommandUsage(String usage) {
        this.commandUsage = usage;
    }

    public String getCommandUsage() {
        return this.commandUsage;
    }
}
