package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Treats.*;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 18.10.2015.
 */
public class Treat
{
    private static final List<Treatment> treats = new ArrayList<>();

    public Treat(TrickOrTreat plugin)
    {
        treats.add(new Explosion());
        treats.add(new Teleport());
        treats.add(new Flames());
        treats.add(new Lightning());
    }

    public void treat(Player player)
    {
        treats.get(((int) (Math.random()*100)) % treats.size())
                .effect(player);
    }

    public void treat(Player player, Treatment treatment)
    {
        treatment.effect(player);
    }
}
