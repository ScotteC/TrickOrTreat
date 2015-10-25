package net.scottec.TrickOrTreat.Tricks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Sugar extends Candy
{
    public Sugar()
    {
        this.name = "Sugar";
        this.item = Material.SUGAR;
        this.lore = new String[1];
        this.lore[0] = "Sweet powder of joy";

        this.itemStack = this.createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SPEED, 10*20, 3));
    }
}
