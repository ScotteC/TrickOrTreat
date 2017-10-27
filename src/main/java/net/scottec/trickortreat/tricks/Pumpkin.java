package net.scottec.trickortreat.tricks;

import net.scottec.trickortreat.TrickOrTreat;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Pumpkin implements Trick {

    private TrickOrTreat.ITrickOrTreat iToT;
    private Map<UUID, ItemStack> helmets = new HashMap<>();

    public Pumpkin(TrickOrTreat.ITrickOrTreat iToT){
        this.iToT = iToT;
    }

    public void effect(Player player) {
        helmets.put(player.getUniqueId(), player.getInventory().getHelmet());
        player.getInventory().setHelmet(new ItemStack(Material.PUMPKIN));

        player.getWorld().playEffect(
                player.getLocation(), Effect.ENDERDRAGON_GROWL, 5);

        // start timer for helmetreset
        new BukkitRunnable(){
            @Override
            public void run() {
                player.getInventory().setHelmet(helmets.get(player.getUniqueId()));
                helmets.remove(player.getUniqueId());
            }
        }.runTaskLater(this.iToT.getPlugin(), 200L);
    }
}
