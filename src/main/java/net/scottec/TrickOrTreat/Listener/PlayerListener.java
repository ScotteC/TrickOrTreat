package net.scottec.TrickOrTreat.Listener;

import net.scottec.TrickOrTreat.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private TrickOrTreat.ITrickOrTreat iToT;

    public PlayerListener(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;
        this.iToT.getPlugin().getServer().getPluginManager()
                .registerEvents(this, this.iToT.getPlugin());
    }


    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent evt)
    {
        if((evt.getRightClicked() instanceof Player)
                && evt.getPlayer().getItemInHand().hasItemMeta())
        {
            if (!(evt.getPlayer().getItemInHand().getItemMeta().hasDisplayName()))
                return;

            if(evt.getPlayer().getItemInHand().getItemMeta().getDisplayName()
                    .equals(Config.getTxt().getString("halloweenstick.name")))
            {
                adapter.getRequestHandler().prepareRequest(evt.getPlayer(),
                        (Player) evt.getRightClicked());
            }

            else if (adapter.getTrick().getCandyByName(evt.getPlayer().getItemInHand()
                    .getItemMeta().getDisplayName()) != null)
            {
                adapter.getTrick().shareCandy(evt.getPlayer(),
                        (Player) evt.getRightClicked());
            }
        }
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();

        if (evt.getAction() == Action.RIGHT_CLICK_AIR
                || evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // return if clicking item cant be a ghostshard
            ItemStack itemInHand = evt.getItem();
            if (itemInHand == null
                    || !itemInHand.hasItemMeta()
                    || !itemInHand.getItemMeta().hasDisplayName()
                    || itemInHand.getType() == Material.BOW)
                return;

            // check if itemInHand is a full stack of 64 ghostshards
            if (itemInHand.getItemMeta().getDisplayName().equals(
                    util.getString("GHOST_SHARD_NAME"))) {
                if (itemInHand.getAmount() == 64) {
                    // these are enough ghostshards, remove them
                    player.getInventory().removeItem(itemInHand);
                    player.updateInventory();
                    // give the player a votecoin and some nice message
                    iToT.getDatabase().addMoney(player.getName(), 1);
                    player.sendMessage(util.getString("GHOST_SHARD_SUCCESS"));
                    util.getWorld().playEffect(player.getLocation(), Effect.FIREWORKS_SPARK, 5);
                }
                // not enough shards
                else
                    player.sendMessage(util.getString("GHOST_SHARD_DENIED"));
            }
        }
    }


    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent evt)
    {
        Player player = evt.getPlayer();

        if(player.getItemInHand().hasItemMeta())
            adapter.getTrick().eatCandy(player);
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

        adapter.getGhost().spawnGhost();

        evt.getPlayer().setSaturation(1);
        evt.getPlayer().setFoodLevel(19);
    }
}
