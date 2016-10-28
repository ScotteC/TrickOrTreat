package net.scottec.TrickOrTreat.Items.Halloweenstick;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HalloweenstickEvent extends Event {
    private static HandlerList handlers = new HandlerList();

    private Player player;
    private Player target;

    public HalloweenstickEvent(Player player, Player target) {
        this.player = player;
        this.target = target;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Player getTarget() {
        return this.target;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
