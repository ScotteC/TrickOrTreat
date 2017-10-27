package net.scottec.trickortreat.handler;

import net.scottec.trickortreat.Config;
import net.scottec.trickortreat.Request;
import net.scottec.trickortreat.treats.*;
import net.scottec.trickortreat.treats.Treat;
import net.scottec.trickortreat.TrickOrTreat;
import net.scottec.trickortreat.util;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TreatHandler {
    private List<Treat> treats = new ArrayList<>();
    private TrickOrTreat.ITrickOrTreat iToT;
    private int loveCount;
    private int loveDelay;
    private int cnt;

    public TreatHandler(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;
        treats.add(new Pumpkin());
        treats.add(new Cookie());
        treats.add(new BakedApple());
        treats.add(new BrainStew());
        treats.add(new RibOfCryy());
        treats.add(new Sirup());

        this.loveCount = Config.getCfg().getInt("treat.lovecount");
        this.loveDelay = Config.getCfg().getInt("treat.lovedelay");
        this.cnt = (20 / loveDelay) * loveCount;
    }

    public ItemStack getRandomTreat() {
        return treats.get(((int) (Math.random() * 100)) % treats.size()).getItem();
    }

    public Treat getTreatByItem(ItemStack stack) {
        for(Treat treat : treats)
            if (treat.getItem().isSimilar(stack))
                return treat;
        return null;
    }

    public void addAllTreats(Player player) {
        for (Treat treat : treats)
            player.getInventory().addItem(treat.getItem());
    }

    public void shareTreat(Player alice, Player bob, EquipmentSlot hand) {
        // look for request from Bob to Alice
        Request request = iToT.getRequestHandler().getRequest(bob, alice);

        // is there an open request?
        if (request != null && !request.getStatus()) {
            // find item in hand
            ItemStack clone;
            if (hand.equals(EquipmentSlot.HAND))
                clone = alice.getInventory().getItemInMainHand().clone();
            else
                clone = alice.getInventory().getItemInOffHand().clone();
            clone.setAmount(1);
            // share treat
            alice.getInventory().removeItem(clone);
            bob.getInventory().addItem(clone);
            // request successfull -> true
            request.setStatus(true);

            // play some nice hearteffects on alice
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (cnt <= 1)
                        this.cancel();
                    else {
                        alice.getWorld().playEffect(alice.getLocation(), Effect.HEART, 1);
                        cnt--;
                    }
                }
            }.runTaskTimer(this.iToT.getPlugin(), 0L, this.loveDelay);
        }
    }

    public void eatTreaty(Player bob, ItemStack item) {
        Treat getTreat = getTreatByItem(item);
        if (getTreat != null)
            // call effect method
            getTreat.effect(bob);
    }
}
