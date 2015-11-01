package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
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
            this.name = Config.getTxt().getString("candy.cookie.name");
            this.item = Material.COOKIE;
            this.lore = Config.getTxt().getStringList("candy.cookie.lore");

            this.itemStack = createCandyItem();
        }

        public void effect(Player player)
        {
            player.addPotionEffect(new PotionEffect(
                    PotionEffectType.SPEED, 10*20, 5),true);
        }
}
