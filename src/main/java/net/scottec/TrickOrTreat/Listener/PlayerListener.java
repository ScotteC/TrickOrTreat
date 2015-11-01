package net.scottec.TrickOrTreat.Listener;

import net.scottec.TrickOrTreat.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Fabian on 30.10.2015.
 */
public class PlayerListener implements Listener
{
    public PlayerListener(JavaPlugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent evt)
    {
        if((evt.getRightClicked() instanceof Player)
                && evt.getPlayer().getItemInHand().hasItemMeta())
        {
            if(evt.getPlayer().getItemInHand().getItemMeta().getDisplayName()
                    .equals(Config.getTxt().getString("halloweenstick.name")))
            {
                TrickOrTreat.oRequestHandler.prepareRequest(evt.getPlayer(),
                        (Player) evt.getRightClicked());
            }

            else if (TrickOrTreat.oTrick.getCandyByName(evt.getPlayer().getItemInHand()
                    .getItemMeta().getDisplayName()) != null)
            {
                TrickOrTreat.oTrick.shareCandy(evt.getPlayer(),
                        (Player) evt.getRightClicked());
            }
        }
    }


    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent evt)
    {
        Player player = evt.getPlayer();

        if(player.getItemInHand().hasItemMeta())
            TrickOrTreat.oTrick.eatCandy(player);
    }


    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent evt)
    {
        if (evt.getEntity() instanceof Player)
        {
            evt.setFoodLevel(19);
        }
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent evt)
    {
        evt.getPlayer().setSaturation(1);
        evt.getPlayer().setFoodLevel(19);
        util.giveHalloweenstick(evt.getPlayer());
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt)
    {
        util.giveHalloweenstick(evt.getPlayer());

        TrickOrTreat.oGhost.spawnGhost();

        evt.getPlayer().setSaturation(1);
        evt.getPlayer().setFoodLevel(19);

        evt.getPlayer().getInventory().addItem(TrickOrTreat.oTrick.getRndCandy());
    }
}
