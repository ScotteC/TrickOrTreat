package net.scottec.TrickOrTreat.Ghosts;

import net.scottec.TrickOrTreat.Config;
import net.scottec.TrickOrTreat.Trick;
import net.scottec.TrickOrTreat.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


/**
 * Initial idea and code written by Titian, Oct 2014
 * Edited by Fabian, Oct 2015
 */
public class Ghost implements Listener
{
    private JavaPlugin plugin;

    private static int ghostSwitch = 0;

    private int maxLived;
    private long dropDelay;
    private int dropCount;
    private long cleanInterval;

    private ItemStack ghostscrap;


    public Ghost(JavaPlugin plugin)
    {
        this.plugin = plugin;

        // load config
        this.maxLived = Config.getCfg().getInt("ghost.maxLived");
        this.dropDelay = Config.getCfg().getInt("ghost.dropDelay");
        this.dropCount = Config.getCfg().getInt("ghost.dropCount");
        this.cleanInterval = Config.getCfg().getInt("ghost.cleanInterval");

        this.ghostscrap = util.createItemStack(
                "Ghostscrap", Material.ROTTEN_FLESH,
                new String[]{"Collect and trade", "Test"});

        // scheduled task to remove old ghosts an wake up bats
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                List<Entity> entities = util.getWorld().getEntities();

                for(Entity entity : entities)
                {
                    if (entity instanceof LivingEntity)
                    {
                        if((entity.getType().equals(EntityType.SKELETON)
                            || entity.getType().equals(EntityType.CREEPER)
                            || entity.getType().equals(EntityType.BAT))
                            && entity.getTicksLived() >= maxLived )
                            entity.remove();
                        if (entity.getType().equals(EntityType.BAT))
                        {
                            Bat bat = (Bat) entity;
                            bat.setAwake(true);
                        }
                    }
                }
            }
        }.runTaskTimer(this.plugin, 0L, this.cleanInterval);
    }

    public void spawnGhost(Location loc)
    {
        LivingEntity carrier = (LivingEntity) util.getWorld()
                .spawnEntity(loc, EntityType.BAT);

        carrier.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 99999, 1));

        switch (ghostSwitch)
        {
            case 0:
            {
                LivingEntity passenger = (LivingEntity) util.getWorld()
                        .spawnEntity(loc, EntityType.SKELETON);

                passenger.addPotionEffect(new PotionEffect(
                        PotionEffectType.INVISIBILITY, 99999, 1));
                passenger.getEquipment().setHelmet(
                        new ItemStack(Material.PUMPKIN));
                passenger.getEquipment().setChestplate(
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                passenger.getEquipment().setItemInHand(
                        new ItemStack(Material.GHAST_TEAR));
                carrier.setPassenger(passenger);
                ghostSwitch++;
                break;
            }

            case 1:
            {
                Creeper creeper = (Creeper) util.getWorld()
                        .spawnEntity(loc, EntityType.CREEPER);
                creeper.setPowered(true);
                carrier.setPassenger(creeper);
                ghostSwitch++;
                break;
            }

            case 2:
            {
                carrier.removePotionEffect(PotionEffectType.INVISIBILITY);
                ghostSwitch = 0;
                break;
            }
        }
    }

    public static void killAllGhosts()
    {
        List<Entity> entities = util.getWorld().getEntities();

        for(Entity entity : entities)
        {
            if (entity instanceof LivingEntity
                    && (entity.getType().equals(EntityType.SKELETON)
                    || entity.getType().equals(EntityType.CREEPER)
                    || entity.getType().equals(EntityType.BAT)))
                entity.remove();
        }
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
            if (evt.getEntity().getVehicle() != null
                   && evt.getEntity().getVehicle().getType().equals(EntityType.BAT))
            {
                LivingEntity carrier = (LivingEntity) evt.getEntity().getVehicle();
                carrier.removePotionEffect(PotionEffectType.INVISIBILITY);
                carrier.remove();
            }

            // cancel natural drops of entity
            evt.setDroppedExp(0);
            evt.getDrops().clear();

            Location loc = evt.getEntity().getLocation();

            new BukkitRunnable()
            {
                int cnt = dropCount;

                @Override
                public void run()
                {
                    if(cnt == 0)
                        this.cancel();
                    else
                    {
                        util.dropItem(loc, Trick.getRndCandy(), 1);
                        util.dropItem(loc, ghostscrap, 2);
                        cnt--;
                    }
                }
            }.runTaskTimer(this.plugin, 0, this.dropDelay);
        }

        // if died entity is a bat
        else if (evt.getEntityType().equals(EntityType.BAT))
        {
            util.dropItem(evt.getEntity().getLocation(), Trick.getRndCandy(), 1);
            util.dropItem(evt.getEntity().getLocation(), ghostscrap, 1);
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
