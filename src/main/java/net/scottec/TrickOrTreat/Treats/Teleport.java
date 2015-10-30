package net.scottec.TrickOrTreat.Treats;

import net.scottec.TrickOrTreat.Config;
import net.scottec.TrickOrTreat.util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 25.10.2015.
 */
public class Teleport implements Treatment
{
    private static List<Location> locations = new ArrayList<>();

    public Teleport()
    {
        List<String> locStrings = Config.getCfg().getStringList("treat.port");
        Location loc;
        String[] split;

        for(String locString : locStrings)
        {
            split = locString.split(":");
            if(split.length == 5)
            {
                loc = new Location(util.getWorld(),
                        Double.parseDouble(split[0]),
                        Double.parseDouble(split[1]),
                        Double.parseDouble(split[2]),
                        Float.parseFloat(split[3]),
                        Float.parseFloat(split[4]));
                locations.add(loc);
            }
        }
    }

    public void effect(Player player)
    {
        player.teleport(locations.get((int)(Math.random()*100) % locations.size()));
    }
}
