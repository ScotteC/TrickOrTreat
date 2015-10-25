package net.scottec.TrickOrTreat.Treats;

import org.bukkit.entity.Player;

/**
 * Created by Fabian on 25.10.2015.
 */
public abstract class Treatment
{
    private String name;

    public abstract void effect(Player player);
}
