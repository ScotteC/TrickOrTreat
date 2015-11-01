package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 01.11.2015.
 */
public class Pumpkin extends Candy
{
    public Pumpkin()
    {
        this.name = Config.getTxt().getString("candy.pumpkin.name");
        this.item = Material.PUMPKIN_PIE;
        this.lore = Config.getTxt().getStringList("candy.pumpkin.lore");

        this.itemStack = createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.JUMP, 10*20, 5),true);
    }
}
