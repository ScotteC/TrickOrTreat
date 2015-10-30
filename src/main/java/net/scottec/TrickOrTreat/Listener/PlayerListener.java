package net.scottec.TrickOrTreat.Listener;

import net.scottec.TrickOrTreat.*;
import net.scottec.TrickOrTreat.Ghosts.Ghost;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
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
                evt.getPlayer().sendMessage("Event1");
                TrickOrTreat.oRequestHandler.prepareRequest(evt.getPlayer(),
                        (Player) evt.getRightClicked());
            }

            else if (TrickOrTreat.oTrick.getCandyByName(evt.getPlayer().getItemInHand()
                    .getItemMeta().getDisplayName()) != null)
            {
                evt.getPlayer().sendMessage("Event2");
                TrickOrTreat.oTrick.shareCandy(evt.getPlayer(),
                        (Player) evt.getRightClicked());
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt)
    {
        if( (evt.getAction().equals(Action.RIGHT_CLICK_AIR)
                || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                && evt.getPlayer().getItemInHand().hasItemMeta()
                && !(evt.getClickedBlock().getState() instanceof InventoryHolder) )
        {
            evt.getPlayer().sendMessage("Event3");
            TrickOrTreat.oTrick.eatCandy(evt.getPlayer());
        }
    }

    /*
     * Check on player join, if player already has an Halloweenstick/Magic Wand
     * in inventory, otherwise add one
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt)
    {
        ItemStack halloweenstick = util.createItemStack(
                Config.getTxt().getString("halloweenstick.name"),
                Material.BLAZE_ROD,
                Config.getTxt().getStringList("halloweenstick.lore"));

        if (!evt.getPlayer().getInventory().contains(halloweenstick))
            evt.getPlayer().getInventory().addItem(halloweenstick);

        TrickOrTreat.oGhost.spawnGhost(evt.getPlayer().getLocation());
    }

}
