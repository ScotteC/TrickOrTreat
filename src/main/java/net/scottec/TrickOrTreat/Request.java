package net.scottec.TrickOrTreat;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Fabian on 22.10.2015.
 *
 * Defines a TrickOrTreat-Request from player Bob to player Alice
 *
 * On construction a new task is scheduled to represent the countdown
 * Request-Object will be deleted by another task scheduled by RequestHandler
 * after specified delay
 */

public class Request {
    private UUID bob;
    private UUID alice;
    private boolean status;
    private long time;
    private int countdown;

    /*
     * constructor
     * starts also runnable task for countdown
     */
    public Request(TrickOrTreat.ITrickOrTreat iToT,
                   UUID bob, UUID alice, int timeout) {
        this.bob = bob;
        this.alice = alice;

        // init request status
        this.status = false;

        // set time of request and top of countdown
        this.time = System.currentTimeMillis();
        this.countdown = timeout;

        // inform bob and alice about started request
        iToT.getCSMessageHandler().sendTitleMessage(Bukkit.getPlayer(bob),
                util.getString("REQUEST_NEW_BOB_TITLE"),
                util.getString("REQUEST_NEW_BOB_SUBTITLE", Bukkit.getPlayer(alice).getName()));

        iToT.getCSMessageHandler().sendTitleMessage(Bukkit.getPlayer(alice),
                util.getString("REQUEST_NEW_ALICE_TITLE"),
                util.getString("REQUEST_NEW_ALICE_SUBTITLE", Bukkit.getPlayer(bob).getName()));
        Bukkit.getPlayer(alice).sendMessage(util.getString("REQUEST_NEW_ALICE_CLUE", Bukkit.getPlayer(bob).getName()));

        // create new task to handle countdown until trick, abort if treated
        new BukkitRunnable() {
            @Override
            public void run() {
                Player pBob = Bukkit.getPlayer(bob);
                Player pAlice = Bukkit.getPlayer(alice);

                iToT.getCSMessageHandler().sendActionBarMessage(pBob, "Countdown: " + countdown);
                iToT.getCSMessageHandler().sendActionBarMessage(pAlice, "Countdown: " + countdown);

                // cancel task and finish request if status is set true
                if (status) {
                    this.cancel();

                    iToT.getCSMessageHandler().sendTitleMessage(pBob,
                            util.getString("REQUEST_SUCCESS_BOB_TITLE"),
                            util.getString("REQUEST_SUCCESS_BOB_SUBTITLE"));
                    iToT.getCSMessageHandler().sendTitleMessage(pAlice,
                            util.getString("REQUEST_SUCCESS_ALICE_TITLE"),
                            util.getString("REQUEST_SUCCESS_ALICE_SUBTITLE"));
                }
                // countdown if alice hasnt jet treated bob
                else if (!status && countdown > 0) {
                    countdown--;
                }
                // finish request on countdown reaching 0
                else if (countdown == 0) {
                    this.cancel();

                    iToT.getCSMessageHandler().sendTitleMessage(pBob,
                            util.getString("REQUEST_DENIED_BOB_TITLE"),
                            util.getString("REQUEST_DENIED_BOB_SUBTITLE"));
                    iToT.getCSMessageHandler().sendTitleMessage(pAlice,
                            util.getString("REQUEST_DENIED_ALICE_TITLE"),
                            util.getString("REQUEST_DENIED_ALICE_SUBTITLE"));
                    // trick alice
                    iToT.getTrickHandler().trick(pAlice);
                }
            }
        }.runTaskTimer(iToT.getPlugin(), 0L, 20L);
        // run task every second without delay until cancelled
    }

    // Getter
    public UUID getBob() {
        return this.bob;
    }

    public UUID getAlice() {
        return alice;
    }

    public long getRequestTime() {
        return this.time;
    }

    public boolean getStatus() {
        return this.status;
    }

    // Setter
    public void setStatus(Boolean status) {
        this.status = status;
    }
}
