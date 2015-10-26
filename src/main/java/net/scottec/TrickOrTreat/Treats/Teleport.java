package net.scottec.TrickOrTreat.Treats;

import org.bukkit.entity.Player;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Teleport implements Treatment
{
    public void effect(Player player)
    {
        player.sendMessage("Energize...");
    }
}
