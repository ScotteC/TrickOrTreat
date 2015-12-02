package net.scottec.TrickOrTreat.Listener;

import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by Fabian on 30.10.2015.
 */
public class EntityListener implements Listener
{
    private TrickOrTreat plugin;
    private TrickOrTreat.ToTFace adapter;

    public EntityListener(TrickOrTreat plugin, TrickOrTreat.ToTFace adapter)
    {
        this.plugin = plugin;
        this.adapter = adapter;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent evt)
    {
        // if died entity is a creeper or skeleton
        if (evt.getEntityType().equals(EntityType.CREEPER)
                || evt.getEntityType().equals(EntityType.SKELETON))
        {
            // if entity rides a bat, remove the bat
            if (evt.getEntity().getVehicle() != null
                    && evt.getEntity().getVehicle().getType().equals(EntityType.BAT))
            {
                evt.getEntity().getVehicle().remove();
            }

            // cancel natural drops of entity
            evt.setDroppedExp(0);
            evt.getDrops().clear();

            // spawn custom drops
            adapter.getGhost().spawnDrops(evt.getEntity(), true);
        }

        // if died entity is a bat
        else if (evt.getEntityType().equals(EntityType.BAT))
        {
            adapter.getGhost().spawnDrops(evt.getEntity(), false);
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
}
