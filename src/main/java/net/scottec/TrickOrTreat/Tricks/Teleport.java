package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
import net.scottec.TrickOrTreat.util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Teleport - Trickeffect
 * The player is teleported to a random location
 */

public class Teleport implements Trick {
    private static List<Location> locations = new ArrayList<>();

    public Teleport() {
        List<String> locStrings = Config.getCfg().getStringList("treat.port");
        Location loc;

        for (String locString : locStrings) {
            loc = util.getLocationFromString(locString);
            if (loc != null)
                locations.add(loc);
        }
    }

    public void effect(Player player) {
        player.teleport(locations.get((int) (Math.random() * 100) % locations.size()));
    }
}
