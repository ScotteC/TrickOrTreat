package net.scottec.TrickOrTreat.Items.Halloweenstick;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;


public class HalloweenstickEvent extends Event {
    private static HandlerList handlers = new HandlerList();

    private Player player;
    private Player target;
    private EquipmentSlot hand;

    public HalloweenstickEvent(Player player, Player target, EquipmentSlot hand) {
        this.player = player;
        this.target = target;
        this.hand = hand;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Player getTarget() {
        return this.target;
    }

    public EquipmentSlot getHand() {
        return this.hand;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
