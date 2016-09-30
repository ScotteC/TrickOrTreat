package net.scottec.TrickOrTreat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


/**
 * Initial idea and code written by Titian, Oct 2014
 * Edited by Fabian, Oct 2015
 */
public class Ghost {
    private static int ghostSwitch = 0;
    private TrickOrTreat.ITrickOrTreat iToT;
    private long lastSpawn;
    private int spawnCooldown;
    private int maxLived;
    private long dropDelay;
    private int dropCount;
    private long cleanInterval;

    private ItemStack ghostscrap;
    private ItemStack coinshard;

    public Ghost(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;

        // load config
        this.lastSpawn = System.currentTimeMillis();
        this.spawnCooldown = Config.getCfg().getInt("ghost.spawncooldown");
        this.maxLived = Config.getCfg().getInt("ghost.maxLived");
        this.dropDelay = Config.getCfg().getInt("ghost.dropDelay");
        this.dropCount = Config.getCfg().getInt("ghost.dropCount");
        this.cleanInterval = Config.getCfg().getInt("ghost.cleanInterval");

        this.ghostscrap = util.createItemStack(
                util.getString("GHOST_SCRAP_NAME"),
                Material.ROTTEN_FLESH,
                util.getStringList("GHOST_SCRAP_LORE"));

        this.coinshard = util.createItemStack(
                util.getString("GHOST_SHARD_NAME"),
                Material.DIAMOND,
                util.getStringList("GHOST_SHARD_LORE"));

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
        }.runTaskTimer(this.iToT.getPlugin(), 0L, this.cleanInterval);
    }


    public void spawnGhost()
    {
        if ((System.currentTimeMillis() - lastSpawn) < spawnCooldown)
            return;

        lastSpawn = System.currentTimeMillis();

        Location loc = util.getLocationFromString(
                Config.getCfg().getString("ghost.spawn"));

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
                creeper.addPotionEffect(new PotionEffect(
                        PotionEffectType.INVISIBILITY, 99999, 1));
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


    public void spawnDrops(LivingEntity entity, Boolean runnable)
    {
        Location loc = entity.getLocation();

        if (runnable)
        {
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
                        if (cnt % 3 == 0)
                            util.dropItem(loc, adapter.getTrick().getRndCandy(), 1);

                        util.dropItem(loc, ghostscrap, 2);
                        util.dropItem(loc, coinshard, 2);
                        cnt--;
                    }
                }
            }.runTaskTimer(this.iToT.getPlugin(), 0, this.dropDelay);
        } else {
            util.dropItem(loc, iToT.getTreatHandler().getRandomTreat(), 1);
            util.dropItem(loc, ghostscrap, 1);
            util.dropItem(loc, coinshard, 3);
        }
    }


    public void killAllGhosts()
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
}
