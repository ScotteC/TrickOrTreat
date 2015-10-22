package net.scottec.TrickOrTreat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fabian on 22.10.2015.
 */
public class RequestHandler implements Listener
{
    private JavaPlugin plugin;

    public final int requestTimeout = 15;
    public final long requestCooldown = 25;

    public static Map<Player, Request> requests = new HashMap<>();

    public RequestHandler(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    /*
     * register request creating instance if ToTRequest to save involved
     * players and current time and store it in hashmap
     * also start delayed task to delete request after timeout
     */
    private void createRequest(Player bob, Player alice)
    {
        Request newRequest = new Request(this.plugin, bob, alice, this.requestTimeout);
        requests.put(bob, newRequest);

        // inform bob and alice about started request
        bob.sendMessage("Trick or Treat, " + alice.getDisplayName() + "!");
        alice.sendMessage(bob.getDisplayName() + ": Trick or Treat, "
                + alice.getDisplayName() );

        // create schedueled task to remove requestobject form map
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                requests.remove(bob, newRequest);
            }
        }.runTaskLater(this.plugin, (this.requestCooldown*20));
    }

    /*
     * if bob rightclicks alice with "Magic Wand" check if theres a pending
     * request by bob, otherwise create a new one
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent evt)
    {
        // requests only on players and triggerd by "Magic Wand"(Blaze rod)
        if((evt.getRightClicked() instanceof Player)
                && (evt.getPlayer().getItemInHand()
                .getType().equals(Material.BLAZE_ROD)))
        //.getItemMeta().getDisplayName().equals("BLAZE ROD"))
        {
            Player bob = evt.getPlayer();
            Player alice = (Player) evt.getRightClicked();

            // check on pending requests from bob,
            // send error based on requeststatus
            if (requests.containsKey(bob))
            {
                if (!requests.get(bob).getStatus())
                    bob.sendMessage("Only one request a time...");
                else
                {
                    long remainCool = (requests.get(bob).getRequestTime()
                            + (this.requestCooldown*1000)
                            - System.currentTimeMillis()) / 1000;
                    bob.sendMessage("Cooldown: " + remainCool + " Seconds");
                }
            }
            // no pending requests, so create one
            else
            {
                createRequest(bob, alice);
            }
        }
    }

    /*
     * check if theres a request from bob on alice
     */
    public static Request checkRequest(Player bob, Player alice)
    {
        Request request = requests.get(bob);
        if (request != null && request.getAlice() == alice)
            return request;
        return null;
    }
}
