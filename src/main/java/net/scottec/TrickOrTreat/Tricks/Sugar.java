package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
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
        this.name = Config.getTxt().getString("candy.sugar.name");
        this.item = Material.SUGAR;
        this.lore = Config.getTxt().getStringList("candy.sugar.lore");

        this.itemStack = this.createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SPEED, 10*20, 3));
    }
}
