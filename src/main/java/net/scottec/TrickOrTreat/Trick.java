package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Tricks.*;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Fabian on 18.10.2015.
 */
public class Trick
{
    private static final List<Candy> candyObjects = new ArrayList<>();

    public Trick(JavaPlugin plugin)
    {
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
            if (candy.getName().equals(name))
                return candy;
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
        }
    }
}
