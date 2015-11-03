package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 01.11.2015.
 */
public class BakedApple extends Candy
{
    public BakedApple()
    {
        this.name = Config.getTxt().getString("candy.bakedapple.name");
        this.item = Material.APPLE;
        this.lore = Config.getTxt().getStringList("candy.bakedapple.lore");

        this.itemStack = createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 10*20, 1));
    }
}
