package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 01.11.2015.
 */
public class RipOfCryy extends Candy
{
    public RipOfCryy()
    {
        this.name = Config.getTxt().getString("candy.ripofcryy.name");
        this.item = Material.COOKED_MUTTON;
        this.lore = Config.getTxt().getStringList("candy.ripofcryy.lore");

        this.itemStack = createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INCREASE_DAMAGE, 10*20, 10),true);
    }
}
