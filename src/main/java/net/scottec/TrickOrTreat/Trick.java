package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Tricks.*;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Fabian on 18.10.2015.
 */
public class Trick implements Listener
{
    private static final List<Candy> candyObjects = new ArrayList<>();

    public Trick(JavaPlugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        candyObjects.add(new Sugar());
        candyObjects.add(new Cookie());
        candyObjects.add(new Sirup());
    }


    public ItemStack getRndCandy()
    {
        return candyObjects.get(
                ((int) (Math.random()*100)) % candyObjects.size())
                .getItemStack();
    }


    public Candy getCandyByName(String name)
    {
        for(Candy candy : candyObjects)
        {
            if (candy.getName().equals(name))
                return candy;
        }
        return null;
    }


    public void shareCandy(Player bob, Player alice)
    {
        Request request = TrickOrTreat.oRequestHandler.checkRequest(bob, alice);
        if (request != null && !request.getStatus())
        {
            ItemStack is = alice.getItemInHand();
            ItemStack clone = is.clone();
            clone.setAmount(1);

            alice.getInventory().removeItem(clone);
            bob.getInventory().addItem(clone);

            request.setStatus(true);
        }
    }


    public void eatCandy(Player bob)
    {
        Candy getCandy = getCandyByName(
                bob.getItemInHand().getItemMeta().getDisplayName());

        if (getCandy != null)
        {
            // call effect method
            getCandy.effect(bob);

            ItemStack is = bob.getItemInHand();
            ItemStack clone = is.clone();
            clone.setAmount(1);
            bob.getInventory().removeItem(clone);
        }
    }

//    @EventHandler
//    public void onPlayerIneractEntity(PlayerInteractEntityEvent evt)
//    {
//        if(evt.getRightClicked() instanceof Player
//                && (evt.getPlayer().getItemInHand().hasItemMeta()))
//        {
//            Request request = RequestHandler.checkRequest(
//                    (Player) evt.getRightClicked(), evt.getPlayer());
//
//            if (request != null && !request.getStatus()
//                    && getCandyByName(evt.getPlayer().getItemInHand()
//                        .getItemMeta().getDisplayName()) != null)
//            {
//                Player alice = evt.getPlayer();
//                Player bob = (Player) evt.getRightClicked();
//
//                ItemStack is = alice.getItemInHand();
//                is.setAmount(is.getAmount() - 1);
//                alice.getInventory().setItem(
//                        alice.getInventory().getHeldItemSlot(), is);
//
//                is.setAmount(1);
//                bob.getInventory().addItem(is);
//
//                request.setStatus(true);
//            }
//        }
//    }

//
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent evt)
//    {
//        Player player = evt.getPlayer();
//
//        if((evt.getAction().equals(Action.RIGHT_CLICK_AIR)
//                || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))
//                && player.getItemInHand().hasItemMeta() )
//        {
//            Candy getCandy = getCandyByName(
//                    player.getItemInHand().getItemMeta().getDisplayName());
//
//            if (getCandy != null)
//            {
//                // call effect method
//                getCandy.effect(player);
//
//                ItemStack is = player.getItemInHand();
//                is.setAmount(is.getAmount() - 1);
//                player.getInventory().setItem(
//                        player.getInventory().getHeldItemSlot(), is);
//            }
//        }
//    }
}
