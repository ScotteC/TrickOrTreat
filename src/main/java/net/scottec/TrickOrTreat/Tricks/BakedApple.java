package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
import net.scottec.TrickOrTreat.TrickOrTreat;
import net.scottec.TrickOrTreat.util;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Fabian on 01.11.2015.
 */
public class BakedApple extends Candy
{
    private int count = 10;
    private int freq = 10;
    private int cnt;

    public BakedApple()
    {
        this.name = Config.getTxt().getString("candy.bakedapple.name");
        this.item = Material.APPLE;
        this.lore = Config.getTxt().getStringList("candy.bakedapple.lore");

        this.itemStack = createCandyItem();

        this.cnt = (20 / freq) * count;
    }

    public void effect(Player player)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (cnt <= 1)
                    this.cancel();
                else
                {
                    util.getWorld().playEffect(player.getLocation(), Effect.HEART, 1);
                    util.getWorld().playEffect(player.getLocation(), Effect.POTION_SWIRL, 1);
                    cnt--;
                }
            }
        }.runTaskTimer(TrickOrTreat.plugin, 0L, freq);
    }
}
