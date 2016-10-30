package net.scottec.TrickOrTreat.Handler;

import net.scottec.TrickOrTreat.TrickOrTreat;
import net.scottec.TrickOrTreat.Tricks.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrickHandler {
    private static final List<String> trickNames = new ArrayList<>();
    private static final Map<String, Trick> tricks = new HashMap<>();

    public TrickHandler(TrickOrTreat.ITrickOrTreat iToT) {
        addTrick(new Explosion(), "explosion");
        addTrick(new Teleport(), "teleport");
        addTrick(new Flames(), "flames");
        addTrick(new Lightning(), "lightning");
        addTrick(new Pumpkin(iToT), "pumpkin");
    }

    private void addTrick(Trick trick, String trickName) {
        tricks.put(trickName, trick);
        trickNames.add(trickName);
    }

    public Trick getTrickByName(String name) {
        return tricks.get(name);
    }

    public void trick(Player player) {
        if (player != null)
            tricks.get(trickNames.get((int)(Math.random() * 100) % trickNames.size())).effect(player);
    }
}
