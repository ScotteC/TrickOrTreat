package net.scottec.TrickOrTreat;

import net.scottec.TrickOrTreat.Tricks.*;
import net.scottec.TrickOrTreat.Tricks.Trick;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TrickHandler {
    private static final List<Trick> treats = new ArrayList<>();

    public TrickHandler(TrickOrTreat.ITrickOrTreat iToT) {
        treats.add(new Explosion());
        treats.add(new Teleport());
        treats.add(new Flames());
        treats.add(new Lightning());
    }

    public void treat(Player player) {
        treats.get(((int) (Math.random() * 100)) % treats.size())
                .effect(player);
    }

    public void treat(Player player, Trick trick) {
        trick.effect(player);
    }
}
