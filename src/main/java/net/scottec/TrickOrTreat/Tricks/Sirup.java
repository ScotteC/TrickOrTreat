package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
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
        this.name = Config.getTxt().getString("candy.sirup.name");
        this.item = Material.POTION;
        this.lore = Config.getTxt().getStringList("candy.sirup.lore");

        this.itemStack = this.createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.CONFUSION, 15*20, 3),true);
    }
}
