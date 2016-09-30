package net.scottec.TrickOrTreat;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Fabian on 22.10.2015.
 *
 * Defines a Trick Or Treat-Request from player Bob to player Alice
 *
 * On construction a new task is scheduled to represent the countdown
 * Request-Object will be deleted by another task scheduled by RequestHandler
 * after specified delay
 *
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

        // create new task to handle countdown until treatment
        new BukkitRunnable() {
            @Override
            public void run() {
                Player pBob = Bukkit.getPlayer(bob);
                Player pAlice = Bukkit.getPlayer(alice);

                pBob.sendMessage("Countdown: " + countdown);

                // cancel task if status is set true
                    this.cancel();
                }
                // countdown if alice hasnt jet tricked bob
                else if (!status && countdown > 0)
                {
                    countdown--;
                }
                // treat alice if countdown reaches 0 without reaction from alice
                    status = true;
                    this.cancel();
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

    public long getRequestTime() {return this.time;}
    public boolean getStatus() {return this.status;}

    // Setter
    public void setStatus(Boolean status) {this.status = status;}
}
