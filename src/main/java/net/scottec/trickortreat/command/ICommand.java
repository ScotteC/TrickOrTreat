package net.scottec.trickortreat.command;

import org.bukkit.entity.Player;

import java.util.List;

public interface ICommand {
    void setCommandHandler(CommandHandler commandHandler);

    void execute(Player sender, List<String> args);

    String getCommandName();
    String getCommandUsage();
    List<String> getAliases();

    void setCommandUsage(String usage);
}
