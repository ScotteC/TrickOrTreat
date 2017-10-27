package net.scottec.trickortreat.handler;

import net.scottec.trickortreat.Config;
import net.scottec.trickortreat.TrickOrTreat;
import net.scottec.trickortreat.util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Initial idea and code written by Titian, Oct 2014
 * Edited by Fabian,
 */
public class GhostHandler {
    private static int ghostSwitch = 0;
    private TrickOrTreat.ITrickOrTreat iToT;
    private long lastSpawn;
    private int spawnCooldown;
    private int spawnCount;
    private int maxLived;
    private int dropDelay;
    private int dropCount;
    private long cleanInterval;
    private World world;

    private List<Location> spawnLocations = new ArrayList<>();

    private ItemStack ghostscrap;
    private ItemStack coinshard;

    public GhostHandler(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;

        // load config
        this.lastSpawn = System.currentTimeMillis();
        this.spawnCooldown = Config.getCfg().getInt("ghost.spawncooldown");
        this.spawnCount = Config.getCfg().getInt("ghost.spawnCount");
        this.maxLived = Config.getCfg().getInt("ghost.maxLived");
        this.dropDelay = Config.getCfg().getInt("ghost.dropDelay");
        this.dropCount = Config.getCfg().getInt("ghost.dropCount");
        this.cleanInterval = Config.getCfg().getInt("ghost.cleanInterval");

        // read spawn locations from config
        List<String> locStrings = Config.getCfg().getStringList("ghost.spawn");
        Location loc;
        for (String locString : locStrings) {
            loc = util.getLocationFromString(locString);
            if (loc != null)
                spawnLocations.add(loc);
        }

        if (spawnLocations.size() != 0)
            this.world = spawnLocations.get(0).getWorld();
        else
            this.world = Bukkit.getWorld("world");

        this.ghostscrap = util.createItemStack(
                util.getString("GHOST_SCRAP_NAME"),
                Material.ROTTEN_FLESH,
                util.getStringList("GHOST_SCRAP_LORE"));

        this.coinshard = util.createItemStack(
                util.getString("GHOST_SHARD_NAME"),
                Material.DIAMOND,
                util.getStringList("GHOST_SHARD_LORE"));

        // scheduled task to remove old ghosts an wake up bats
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Entity> entities = world.getEntities();

                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        if ((entity.getType().equals(EntityType.SKELETON)
                                || entity.getType().equals(EntityType.CREEPER)
                                || entity.getType().equals(EntityType.BAT)
                                || entity.getType().equals(EntityType.PIG))
                                && entity.getTicksLived() >= maxLived)
                            entity.remove();
                        if (entity.getType().equals(EntityType.BAT)) {
                            Bat bat = (Bat) entity;
                            bat.setAwake(true);
                        }
                    }
                }
            }
        }.runTaskTimer(this.iToT.getPlugin(), 0L, this.cleanInterval);
    }

    public List<Location> listSpawnLocations() {
        return spawnLocations;
    }

    public void addSpawnLocation(Location newLocation){
        this.spawnLocations.add(newLocation);
        List<String> locStrings = Config.getCfg().getStringList("ghost.spawn");
        locStrings.add(util.getStringFromLocation(newLocation));
        Config.getCfg().set("ghost.spawn", locStrings);
        Config.saveCfg();
    }

    public boolean removeSpawnLocation(int index) {
        if(index >= 0 && index < spawnLocations.size()) {
            spawnLocations.remove(index);
            List<String> locStrings = Config.getCfg().getStringList("ghost.spawn");
            locStrings.remove(index);
            Config.getCfg().set("ghost.spawn", locStrings);
            Config.saveCfg();
            return true;
        }
        return false;
    }

    public int getSpawnCount() {
        return this.spawnCount;
    }

    public boolean setSpawnCount(int count) {
        if(count >= 0) {
            this.spawnCount = count;
            Config.getCfg().set("ghost.spawnCount", count);
            Config.saveCfg();
            return true;
        }
        return false;
    }

    public int[] getDrops() {
        int[] drops = {this.dropCount, this.dropDelay};
        return drops;
    }

    public boolean setDrops(int count, int delay) {
        if(count >= 0 && delay >= 0) {
            this.dropCount = count;
            this.dropDelay = delay;
            Config.getCfg().set("ghost.dropCount", count);
            Config.getCfg().set("ghost.dropDelay", delay);
            Config.saveCfg();
            return true;
        }
        return false;
    }

    public void prepareGhostSpawn() {
        // cooldown for ghostspawn or no spawnlocations specified
        if ((System.currentTimeMillis() - lastSpawn) < spawnCooldown || spawnLocations.isEmpty())
            return;
        lastSpawn = System.currentTimeMillis();

        // randomly choose locations for ghostspawn
        for(int i = 0; i < this.spawnCount; i++)
            spawnGhost(spawnLocations.get((int)((Math.random()*11) % spawnLocations.size())));
    }

    private void spawnGhost(Location location) {
        LivingEntity carrier = (LivingEntity) location.getWorld()
                .spawnEntity(location, EntityType.BAT);

        carrier.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 99999, 1));

        switch (ghostSwitch) {
            case 0: {
                LivingEntity passenger = (LivingEntity) location.getWorld()
                        .spawnEntity(location, EntityType.SKELETON);

                passenger.addPotionEffect(new PotionEffect(
                        PotionEffectType.INVISIBILITY, 99999, 1));
                passenger.getEquipment().setHelmet(
                        new ItemStack(Material.PUMPKIN));
                passenger.getEquipment().setChestplate(
                        new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                passenger.getEquipment().setItemInMainHand(
                        new ItemStack(Material.GHAST_TEAR));
                carrier.setPassenger(passenger);
                ghostSwitch++;
                break;
            }

            case 1: {
                Creeper creeper = (Creeper) location.getWorld()
                        .spawnEntity(location, EntityType.CREEPER);
                creeper.addPotionEffect(new PotionEffect(
                        PotionEffectType.INVISIBILITY, 99999, 1));
                creeper.setPowered(true);
                carrier.setPassenger(creeper);
                ghostSwitch++;
                break;
            }

            case 2:
                Pig passenger = (Pig) location.getWorld()
                        .spawnEntity(location, EntityType.PIG);
                passenger.setCustomNameVisible(false);
                passenger.setCustomName("Dinnerbone");
                carrier.setPassenger(passenger);
                ghostSwitch++;
                break;

            case 3: {
                carrier.removePotionEffect(PotionEffectType.INVISIBILITY);
                ghostSwitch = 0;
                break;
            }
        }
    }

    public void spawnDrops(LivingEntity entity, Boolean runnable) {
        Location loc = entity.getLocation();

        if (runnable) {
            new BukkitRunnable() {
                int cnt = dropCount;

                @Override
                public void run() {
                    if (cnt == 0)
                        this.cancel();
                    else {
                        if (cnt % 3 == 0)
                            util.dropItem(loc, iToT.getTreatHandler().getRandomTreat(), 1);

                        util.dropItem(loc, ghostscrap, 1);
                        util.dropItem(loc, coinshard, 1);
                        cnt--;
                    }
                }
            }.runTaskTimer(this.iToT.getPlugin(), 0, this.dropDelay);
        } else {
            util.dropItem(loc, iToT.getTreatHandler().getRandomTreat(), 1);
            util.dropItem(loc, ghostscrap, 2);
            util.dropItem(loc, coinshard, 2);
        }
    }

    public void killAllGhosts() {
        world.getEntities().forEach(entity -> {
            if (entity instanceof LivingEntity
                    && (entity.getType().equals(EntityType.SKELETON)
                    || entity.getType().equals(EntityType.CREEPER)
                    || entity.getType().equals(EntityType.BAT)
                    || entity.getType().equals(EntityType.PIG)))
                entity.remove();
        });
    }
}
