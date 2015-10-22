package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.RequestHandler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Fabian on 18.10.2015.
 */
public class Trick implements Listener
{
    private final Map<String, PotionEffectType> candy = new HashMap<>();

    public Trick()
    {
        candy.put("Chocolate", PotionEffectType.SPEED);
        candy.put("Cookie", PotionEffectType.JUMP);
        candy.put("Sugar", PotionEffectType.NIGHT_VISION);
        candy.put("Sirup", PotionEffectType.CONFUSION);
    }

    public void addCandy(Player player, int amount)
    {
        ItemStack candy = new ItemStack(Material.COCOA);
        ItemMeta candyMeta = candy.getItemMeta();

        candyMeta.setDisplayName("Chocolate");
        String[] lore = {"Sweet choclate...", "Taste it, it's delicious..."};

        candyMeta.setLore(Arrays.asList(lore));
        candy.setItemMeta(candyMeta);
        //candy.setAmount(amount);

        player.getInventory().setItem(player.getInventory().firstEmpty(), candy);
        player.updateInventory();
    }


    @EventHandler
    public void onPlayerIneractEntity(PlayerInteractEntityEvent evt)
    {
        if(evt.getRightClicked() instanceof Player
                && (!evt.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)))
        {
            Request request = RequestHandler.checkRequest(
                    (Player) evt.getRightClicked(),evt.getPlayer());

            if (request != null && !request.getStatus())
            {
                Player alice = evt.getPlayer();
                Player bob = (Player) evt.getRightClicked();

                ItemStack is = alice.getItemInHand();
                int isIndex  = alice.getInventory().getHeldItemSlot();
                is.setAmount(is.getAmount() - 1);
                alice.getInventory().setItem(isIndex, is);

                is.setAmount(1);
                bob.getInventory().addItem(is);

                request.setStatus(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt)
    {
        Player player = evt.getPlayer();

        if(evt.getAction().equals(Action.RIGHT_CLICK_AIR)
                || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            if (!(player.getItemInHand().hasItemMeta())
                    || player.getItemInHand() == null)
            {
                addCandy(player, 1);
                return;
            }

            String itemName = player.getItemInHand().getItemMeta().getDisplayName();
            if (candy.containsKey(itemName))
            {
                player.addPotionEffect(new PotionEffect(
                        candy.get(itemName), 15, 5));
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt)
    {
        Player player = evt.getPlayer();
        addCandy(player, 1);

//        player.sendMessage("Hallo");
//
//        ItemStack newCandy = new ItemStack(Material.COCOA);
//        ItemMeta newCandyMeta = newCandy.getItemMeta();
//
//        newCandyMeta.setDisplayName("Chocolate");
//        String[] lore = {"Sweet choclate...", "Taste it, it's delicious..."};
//
//        newCandyMeta.setLore(Arrays.asList(lore));
//        newCandy.setItemMeta(newCandyMeta);
//        newCandy.setAmount(1);
//
//        player.getInventory().addItem(newCandy);
//        player.updateInventory();
    }
}
