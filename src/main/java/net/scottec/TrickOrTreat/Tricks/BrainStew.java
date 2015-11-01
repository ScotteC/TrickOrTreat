package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 01.11.2015.
 */
public class BrainStew extends Candy
{
    public BrainStew()
    {
        this.name = Config.getTxt().getString("candy.brainstew.name");
        this.item = Material.COOKED_RABBIT;
        this.lore = Config.getTxt().getStringList("candy.brainstew.lore");

        this.itemStack = createCandyItem();
    }

    public void effect(Player player)
    {
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.NIGHT_VISION, 10*20, 5),true);
    }
}
