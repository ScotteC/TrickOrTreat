package net.scottec.trickortreat.listener;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.scottec.trickortreat.*;
import net.scottec.trickortreat.Items.halloweenstick.Halloweenstick;
import net.scottec.trickortreat.Items.halloweenstick.HalloweenstickEvent;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private TrickOrTreat.ITrickOrTreat iToT;

    public PlayerListener(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;
        this.iToT.getPlugin().getServer().getPluginManager()
                .registerEvents(this, this.iToT.getPlugin());
    }

    /**
     * Someone rightclicked someone other with an halloweenstick
     * @param event : HalloweenstickEvent (custom)
     */
    @EventHandler
    public void onHalloweenstickEvent(HalloweenstickEvent event) {
        // check whether player or target is located in pvp-ALLOW region
        if(this.iToT.getWorldGuard().getRegionManager(event.getPlayer().getWorld())
                .getApplicableRegions(event.getPlayer().getLocation()).testState(null, DefaultFlag.PVP)
                || this.iToT.getWorldGuard().getRegionManager(event.getTarget().getWorld())
                .getApplicableRegions(event.getTarget().getLocation()).testState(null, DefaultFlag.PVP)) {
            // block trickortreat if true, send message
            this.iToT.getCSMessageHandler().sendActionBarMessage(event.getPlayer(), util.getString("REQUEST_PVP"));
        }
        else {
            // start ToTRequest
            this.iToT.getRequestHandler().prepareRequest(event.getPlayer(), event.getTarget());
        }

    }

    /**
     * Share some treats on ToTrequest
     * @param event : PlayerInteractEvent
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if (event.getRightClicked() instanceof Player) {
            if(event.getHand() == EquipmentSlot.HAND
                    && iToT.getTreatHandler().getTreatByItem(event.getPlayer().getInventory().getItemInMainHand()) == null)
                return;
            if(event.getHand() == EquipmentSlot.OFF_HAND
                    && iToT.getTreatHandler().getTreatByItem(event.getPlayer().getInventory().getItemInOffHand()) == null)
                return;

            this.iToT.getTreatHandler().shareTreat(event.getPlayer(), (Player)event.getRightClicked(), event.getHand());
        }
    }

    /**
     * Transfer ghostshards to votecoins
     * @param evt : PlayerInteractEvent
     */
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
                    iToT.getCSVoteHandler().addMoney(player.getName(), 1);
                    this.iToT.getCSMessageHandler().sendActionBarMessage(player, util.getString("GHOST_SHARD_SUCCESS"));
                    evt.getPlayer().getWorld().playEffect(player.getLocation(), Effect.FIREWORKS_SPARK, 5);
                }
                // not enough shards
                else
                    this.iToT.getCSMessageHandler().sendActionBarMessage(player, util.getString("GHOST_SHARD_DENIED"));
            }
        }
    }

    /**
     * A Player eats something, check if its some candy
     * @param event : PlayerItemConsumeEvent
     */
    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (this.iToT.getTreatHandler().getTreatByItem(event.getItem()) != null)
            iToT.getTreatHandler().eatTreaty(event.getPlayer(), event.getItem());
    }

    /**
     * Hold Player on constant FoodLevel, so he can
     * always eat some candy
     * @param evt : FoodLevelChangeEvent
     */
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent evt) {
        if (evt.getEntity() instanceof Player) {
            evt.setFoodLevel(19);
        }
    }

    /**
     * If a Player respawns after death, give him a new Halloweenstick
     * and reset foodlevel and saturation
     * @param evt : PlayerRespawnEvent
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent evt) {
        evt.getPlayer().setSaturation(1);
        evt.getPlayer().setFoodLevel(19);

        ItemStack stick = new Halloweenstick();
        if (!evt.getPlayer().getInventory().containsAtLeast(stick, 1))
            evt.getPlayer().getInventory().addItem(stick);
    }

    /**
     * Give a Halloweenstick to joined Player
     * and spawn a new ghost
     * @param evt : PlayerJoinEvent
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        this.iToT.getGhostHandler().prepareGhostSpawn();
        evt.getPlayer().setSaturation(1);
        evt.getPlayer().setFoodLevel(19);

        ItemStack stick = new Halloweenstick();
        if (!evt.getPlayer().getInventory().containsAtLeast(stick, 1))
            evt.getPlayer().getInventory().addItem(stick);
    }
}
