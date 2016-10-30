package net.scottec.TrickOrTreat.Tricks;

import net.scottec.TrickOrTreat.Config;
import net.scottec.TrickOrTreat.util;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Teleport - Trickeffect
 * The player is teleported to a random location
 */

public class Teleport implements Trick {
    private List<Location> locations = new ArrayList<>();

    public Teleport() {
        List<String> locStrings = Config.getCfg().getStringList("trick.port");
        Location loc;

        for (String locString : locStrings) {
            loc = util.getLocationFromString(locString);
            if (loc != null)
                locations.add(loc);
        }
    }

    public List<Location> listPortLocations() {
        return locations;
    }

    public void addPortLocation(Location newPortLocation){
        this.locations.add(newPortLocation);
        List<String> ports = Config.getCfg().getStringList("trick.port");
        ports.add(newPortLocation.getX() + ":"
                + newPortLocation.getY() + ":"
                + newPortLocation.getZ() + ":"
                + newPortLocation.getYaw() + ":"
                + newPortLocation.getPitch());
        Config.getCfg().set("trick.port", ports);
        Config.saveCfg();
    }

    public boolean removePortLocation(int index) {
        return locations.remove(locations.get(index));
    }

    public void effect(Player player) {
        player.getWorld().playEffect(player.getLocation(), Effect.LARGE_SMOKE, 10);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 1);
        player.teleport(locations.get((int) (Math.random() * 100) % locations.size()));
    }
}
