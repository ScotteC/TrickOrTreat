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
    public void onPlayerInteract(PlayerInteractEvent evt)
    {
        Action action = evt.getAction();
        Player player = evt.getPlayer();

        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
        {
            if (!player.getItemInHand().hasItemMeta()
                    || player.getItemInHand() == null
                    || player.getItemInHand().getType() == Material.BOW)
                return;

            if (!(player.getItemInHand().getItemMeta().hasDisplayName()))
                return;

            ItemStack is = player.getItemInHand();

            if (is.getItemMeta().getDisplayName().equals(
                    Config.getTxt().getString("ghost.shard.name")))
            {
                if(is.getAmount() == 64)
                {
                    player.getInventory().removeItem(is);
                    player.updateInventory();
                    adapter.getMySQL().addMoney(player.getName(), 1);
                    player.sendMessage(
                            Config.getTxt().getString("ghost.shard.success"));
                    return;
                }
                player.sendMessage(
                        Config.getTxt().getString("ghost.shard.denied"));
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
