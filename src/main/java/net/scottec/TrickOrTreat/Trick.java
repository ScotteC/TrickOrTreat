package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Tricks.*;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Fabian on 18.10.2015.
 */
public class Trick
{
    private TrickOrTreat plugin;
    private TrickOrTreat.ToTFace adapter;

    private int loveCount;
    private int loveDelay;
    private int cnt;

    private static final List<Candy> candyObjects = new ArrayList<>();

    public Trick(TrickOrTreat plugin, TrickOrTreat.ToTFace adapter)
    {
        this.plugin = plugin;
        this.adapter = adapter;
        candyObjects.add(new Pumpkin());
        candyObjects.add(new Cookie());
        candyObjects.add(new BakedApple());
        candyObjects.add(new BrainStew());
        candyObjects.add(new RipOfCryy());
        candyObjects.add(new Sirup());

        this.loveCount = Config.getCfg().getInt("trick.lovecount");
        this.loveDelay = Config.getCfg().getInt("trick.lovedelay");
        this.cnt = (20 / loveDelay) * loveCount;
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

    public void addAllCandy(Player player)
    {
        for(Candy candy : candyObjects)
            player.getInventory().addItem(candy.getItemStack());
    }


    public void shareCandy(Player alice, Player bob)
    {
        Request request = adapter.getRequestHandler().checkRequest(bob, alice);
        if (request != null && !request.getStatus())
        {
            ItemStack is = alice.getItemInHand();
            ItemStack clone = is.clone();
            clone.setAmount(1);

            alice.getInventory().removeItem(clone);
            bob.getInventory().addItem(clone);

            request.setStatus(true);

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if (cnt <= 1)
                        this.cancel();
                    else
                    {
                        util.getWorld().playEffect(alice.getLocation(), Effect.HEART, 1);
                        cnt--;
                    }
                }
            }.runTaskTimer(this.plugin, 0L, this.loveDelay);
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
