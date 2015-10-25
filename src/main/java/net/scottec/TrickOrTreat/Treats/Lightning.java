package net.scottec.TrickOrTreat.Treats;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Lightning extends Treatment
{
    public void effect(Player player)
    {
        player.getWorld().strikeLightningEffect(player.getLocation());
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.BLINDNESS, 10*20, 100));
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW, 10*20, 100));
    }
}
