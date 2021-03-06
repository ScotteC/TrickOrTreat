package net.scottec.trickortreat.Items.halloweenstick;

import net.scottec.trickortreat.TrickOrTreat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HalloweenstickListener  implements Listener {
    private TrickOrTreat.ITrickOrTreat iToT;
    private Halloweenstick halloweenstick;

    public HalloweenstickListener(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;
        this.halloweenstick = new Halloweenstick();
        this.iToT.getPlugin().getServer().getPluginManager().registerEvents(this, this.iToT.getPlugin());
    }

    @EventHandler
    public void onPlayerInteractEntityHand(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            if ((event.getHand() == EquipmentSlot.HAND
                    && this.halloweenstick.isStick(event.getPlayer().getInventory().getItemInMainHand()))
                    || (event.getHand() == EquipmentSlot.OFF_HAND
                    && this.halloweenstick.isStick(event.getPlayer().getInventory().getItemInOffHand())))
                this.iToT.getPlugin().getServer().getPluginManager().callEvent(
                        new HalloweenstickEvent(event.getPlayer(), (Player) event.getRightClicked()));
        }
    }
}

