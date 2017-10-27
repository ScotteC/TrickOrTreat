package net.scottec.trickortreat.tricks;

import net.scottec.trickortreat.Config;
import net.scottec.trickortreat.util;
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

    public void addPortLocation(Location newLocation){
        this.locations.add(newLocation);
        List<String> locStrings = Config.getCfg().getStringList("trick.port");
        locStrings.add(util.getStringFromLocation(newLocation));
        Config.getCfg().set("trick.port", locStrings);
        Config.saveCfg();
    }

    public boolean removePortLocation(int index) {
        if(index >= 0 && index < locations.size()) {
            locations.remove(index);
            List<String> ports = Config.getCfg().getStringList("trick.port");
            ports.remove(index);
            Config.getCfg().set("trick.port", ports);
            Config.saveCfg();
            return true;
        }
        return false;
    }

    public void effect(Player player) {
        player.getWorld().playEffect(player.getLocation(), Effect.LARGE_SMOKE, 10);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, 1);
        if (!this.locations.isEmpty())
            player.teleport(locations.get((int) (Math.random() * 100) % locations.size()));
    }
}
