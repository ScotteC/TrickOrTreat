package net.scottec.TrickOrTreat.Handler;

import net.scottec.TrickOrTreat.TrickOrTreat;
import net.scottec.TrickOrTreat.Tricks.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TrickHandler {
    private static final List<Trick> tricks = new ArrayList<>();

    public TrickHandler(TrickOrTreat.ITrickOrTreat iToT) {
        tricks.add(new Explosion());
        tricks.add(new Teleport());
        tricks.add(new Flames());
        tricks.add(new Lightning());
        tricks.add(new Pumpkin(iToT));
    }

    public void trick(Player player) {
        if (player != null)
            tricks.get(((int) (Math.random() * 100)) % tricks.size())
                    .effect(player);
    }
}
