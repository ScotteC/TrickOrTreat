package net.scottec.TrickOrTreat;

import de.craftstuebchen.ysl3000.api.messageapi.MessageAPI;
import de.craftstuebchen.ysl3000.api.messageapi.interfaces.IActionbarManager;
import de.craftstuebchen.ysl3000.api.messageapi.interfaces.ITitleManager;

import org.bukkit.entity.Player;
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

    private Player bob;
    private Player alice;

    private boolean status;

    private long time;
    private int countdown;

    private IActionbarManager actionBar;
    private ITitleManager titleBar;

    /*
     * constructor
     * starts also runnable task for countdown
     */
    public Request(TrickOrTreat.ITrickOrTreat iToT,
                   Player bob, Player alice, int timeout)
    {

        this.actionBar = MessageAPI.inst().getActionbarManager();
        this.titleBar = MessageAPI.inst().getTitleManager();

        this.bob = bob;
        this.alice = alice;

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
                actionBar.sendActionBarMessage(bob,
                        "&6&l>> &4" + countdown + " &6&l<<");
                actionBar.sendActionBarMessage(alice,
                        "&6&l>> &4" + countdown + " &6&l<<");

                // cancel task if status is set true
                if (status)
                {
                    titleBar.sendTitleMessageHeader(bob,
                            Config.getTxt().getString("request.success.bob.header"));
                    titleBar.sendTitleMessageFooter(bob,
                            Config.getTxt().getString("request.success.bob.footer"));
                    titleBar.sendTitleMessageHeader(alice,
                            Config.getTxt().getString("request.success.alice.header"));
                    titleBar.sendTitleMessageFooter(alice,
                            Config.getTxt().getString("request.success.alice.footer"));
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
                    adapter.getTreat().treat(alice);

                    titleBar.sendTitleMessageHeader(bob,
                            Config.getTxt().getString("request.denied.bob.header"));
                    titleBar.sendTitleMessageFooter(bob,
                            Config.getTxt().getString("request.denied.bob.footer"));
                    titleBar.sendTitleMessageHeader(alice,
                            Config.getTxt().getString("request.denied.alice.header"));
                    titleBar.sendTitleMessageFooter(alice,
                            Config.getTxt().getString("request.denied.alice.footer"));

                    status = true;
                    this.cancel();
                }
            }
        }.runTaskTimer(iToT.getPlugin(), 0L, 20L);
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
