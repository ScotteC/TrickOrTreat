package net.scottec.TrickOrTreat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
public class Request
{
    private JavaPlugin plugin;

    private Player bob;
    private Player alice;

    private int boblvl;
    private int alicelvl;

    private boolean status;

    private long time;
    private int countdown;

    /*
     * constructor
     * starts also runnable task for countdown
     */
    public Request(JavaPlugin plugin, Player bob, Player alice, int timeout)
    {
        this.plugin = plugin;

        this.bob = bob;
        this.alice = alice;
        // save bobs and alices actual explevel
        this.boblvl = bob.getLevel();
        this.alicelvl = alice.getLevel();

        // init request status
        this.status = false;

        // set time of request and top of countdown
        this.time = System.currentTimeMillis();
        this.countdown = timeout;

        // create new task to handle countdown until treatment
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // set level as countdown
                bob.setLevel(countdown);
                alice.setLevel(countdown);

                // cancel task if status is set true
                if (status)
                {
                    bob.setLevel(boblvl);
                    alice.setLevel(alicelvl);
                    bob.sendMessage("You recieved some love");
                    alice.sendMessage("Thank you for sharing your sweeties");
                    this.cancel();
                }
                // countdown if alice hasnt jet tricked bob
                else if (!status && countdown > 0)
                {
                    countdown--;
                }
                // treat alice if countdown reaches 0 without reaction from alice
                else if (!status && countdown == 0)
                {
                    bob.setLevel(boblvl);
                    alice.setLevel(alicelvl);
                    Treat.treat(alice);
                    alice.sendMessage("You're very bad... Enjoy your treatment...");
                    bob.sendMessage("Sorry, request denied...");
                    status = true;
                    this.cancel();
                }
            }
        }.runTaskTimer(this.plugin, 0L, 20L);
        // run task every second without delay until cancelled
    }

    // Getter
    public Player getBob() {return this.bob;}
    public Player getAlice() {return this.alice;}
    public long getRequestTime() {return this.time;}
    public boolean getStatus() {return this.status;}

    // Setter
    public void setStatus(Boolean status) {this.status = status;}
}
