package net.scottec.trickortreat.listener;

import net.scottec.trickortreat.TrickOrTreat;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener {
    private TrickOrTreat.ITrickOrTreat iToT;

    public EntityListener(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;
        this.iToT.getPlugin().getServer().getPluginManager()
            .registerEvents(this, this.iToT.getPlugin());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent evt) {
        // if died entity is a creeper or skeleton
        if (evt.getEntityType().equals(EntityType.CREEPER)
                || evt.getEntityType().equals(EntityType.SKELETON)
                || evt.getEntityType().equals(EntityType.PIG)) {
            // if entity rides a bat, remove the bat
            if (evt.getEntity().getVehicle() != null
                    && evt.getEntity().getVehicle().getType().equals(EntityType.BAT))
                evt.getEntity().getVehicle().remove();

            // cancel natural drops of entity
            evt.setDroppedExp(0);
            evt.getDrops().clear();

            // spawn custom drops
            this.iToT.getGhostHandler().spawnDrops(evt.getEntity(), true);
        }

        // if died entity is a bat
        else if (evt.getEntityType().equals(EntityType.BAT))
            this.iToT.getGhostHandler().spawnDrops(evt.getEntity(), false);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent evt) {
        // cancel event, if damaged source is suffocation of an creeper or skeleton
        // also cancel event, if damaged entity is a bat ridden by something
        if ((evt.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION
                && (evt.getEntityType().equals(EntityType.CREEPER)
                || evt.getEntityType().equals(EntityType.SKELETON)
                || evt.getEntityType().equals(EntityType.PIG)))
                || (evt.getEntityType().equals(EntityType.BAT)
                && (evt.getEntity().getPassenger() != null)))
            evt.setCancelled(true);
    }
}
