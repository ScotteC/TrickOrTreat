package net.scottec.TrickOrTreat.Tricks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Sirup extends Candy
{
    public Sirup()
    {
        this.name = "Sirup";
        this.item = Material.POTION;
        this.lore = new String[2];
        this.lore[0] = "Creamy red sirup.";
        this.lore[1] = "Looks like blood...";

        this.itemStack = this.createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.CONFUSION, 15*20, 3));
    }
}
