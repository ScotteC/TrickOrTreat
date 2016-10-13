package net.scottec.TrickOrTreat.Items.Halloweenstick;

import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Created by Fabian on 04.10.2016.
 */
public class HalloweenstickListener  implements Listener {
    TrickOrTreat.ITrickOrTreat iToT;
    public HalloweenstickListener(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;
        this.iToT.getPlugin().getServer().getPluginManager().registerEvents(this, this.iToT.getPlugin());
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player
            && ( (event.getHand().equals(EquipmentSlot.HAND)
                    && event.getPlayer().getInventory().getItemInMainHand().isSimilar(new Halloweenstick()) )
                || (event.getHand().equals(EquipmentSlot.OFF_HAND)
                    && event.getPlayer().getInventory().getItemInOffHand().isSimilar(new Halloweenstick()) ) )) {
            this.iToT.getPlugin().getServer().getPluginManager().callEvent(
                    new HalloweenstickEvent(event.getPlayer(), (Player) event.getRightClicked(), event.getHand()));

        }
    }


}
