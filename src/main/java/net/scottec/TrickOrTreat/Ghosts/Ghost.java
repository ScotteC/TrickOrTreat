package net.scottec.TrickOrTreat.Ghosts;

import net.scottec.TrickOrTreat.Trick;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Initial idea and code written by Titian, Oct 2014
 * Edited by Fabian, Oct 2015
 */
public class Ghost implements Listener
{

    public void spawnGhost(Location l)
    {
        LivingEntity carrier = (LivingEntity) Bukkit.getWorld("world")
                .spawnEntity(l, EntityType.BAT);

        carrier.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 99999, 1));

        LivingEntity passenger = (LivingEntity) Bukkit.getWorld("world")
                .spawnEntity(l, EntityType.SKELETON);

        passenger.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 99999, 1));
        passenger.getEquipment().setHelmet(
                new ItemStack(Material.PUMPKIN));
        passenger.getEquipment().setChestplate(
                new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        passenger.getEquipment().setItemInHand(
                new ItemStack(Material.GHAST_TEAR));

        carrier.setPassenger(passenger);
    }


    /**
     * EventHandler
     */

    @EventHandler
    public void onEntityDeath(EntityDeathEvent evt)
    {
        // if died entity is a creeper or skeleton
        if (evt.getEntityType().equals(EntityType.CREEPER)
                || evt.getEntityType().equals(EntityType.SKELETON))
        {
            // if entity rides a bat, make the bat visibile
            if (evt.getEntity().getVehicle().getType().equals(EntityType.BAT))
            {
                LivingEntity carrier = (LivingEntity) evt.getEntity().getVehicle();
                carrier.removePotionEffect(PotionEffectType.INVISIBILITY);
                carrier.remove();
            }

            // cancel natural drops of entity
            evt.setDroppedExp(0);
            evt.getDrops().clear();

            Bukkit.getServer().getWorld("world").dropItemNaturally(
                    evt.getEntity().getLocation(), Trick.getRndCandy());
        }

        // if died entity is a bat
        else if (evt.getEntityType().equals(EntityType.BAT))
        {

        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent evt)
    {
        // cancel event, if damaged source is suffocation of an creeper or
        // skeleton
        // also cancel event, if damaged entity is a bat ridden by something
        if ( (evt.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION
                && (evt.getEntityType().equals(EntityType.CREEPER)
                    || evt.getEntityType().equals(EntityType.SKELETON)))
            || (evt.getEntityType().equals(EntityType.BAT)
                    && (evt.getEntity().getPassenger() != null)) )
            evt.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt)
    {
        Player player = evt.getPlayer();
        spawnGhost(player.getLocation());
    }
}
