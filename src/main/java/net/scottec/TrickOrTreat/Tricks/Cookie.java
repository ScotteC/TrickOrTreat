package net.scottec.TrickOrTreat.Tricks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Cookie extends Candy
{
        public Cookie()
        {
            this.name = "Sweet Cookie";
            this.item = Material.COOKIE;
            this.lore = new String[1];
            this.lore[0] = "Very creepy cookie";

            this.itemStack = this.createCandyItem();
        }

        public void effect(Player player)
        {
            player.addPotionEffect(new PotionEffect(
                    PotionEffectType.JUMP, 10*20, 5));
        }
}
