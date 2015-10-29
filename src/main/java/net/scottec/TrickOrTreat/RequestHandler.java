package net.scottec.TrickOrTreat;

import de.craftstuebchen.ysl3000.api.messageapi.MessageAPI;
import de.craftstuebchen.ysl3000.api.messageapi.interfaces.IActionbarManager;
import de.craftstuebchen.ysl3000.api.messageapi.interfaces.ITitleManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
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

    private int requestTimeout;
    private long requestCooldown;

    private IActionbarManager actionBar;
    private ITitleManager titleBar;

    public static Map<Player, Request> requests = new HashMap<>();

    /*
     * constructor
     */
    public RequestHandler(JavaPlugin plugin)
    {
        this.plugin = plugin;

        this.requestTimeout = this.plugin.getConfig()
                .getInt("request.requestTimeout");
        this.requestCooldown = this.plugin.getConfig()
                .getLong("request.requestCooldown");

        this.actionBar = MessageAPI.inst().getActionbarManager();
        this.titleBar = MessageAPI.inst().getTitleManager();
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
        titleBar.sendTitleMessageHeader(bob,
                "&6Trick or Treat!");
        titleBar.sendTitleMessageFooter(bob,
                "&6Send request to &4" + alice.getDisplayName());
        titleBar.sendTitleMessageHeader(alice,
                "&6Trick or Treat!");
        titleBar.sendTitleMessageFooter(alice,
                "&6Request from &4" + bob.getDisplayName());

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
     * check if theres a request from bob on alice
     */
    public static Request checkRequest(Player bob, Player alice)
    {
        Request request = requests.get(bob);
        if (request != null && request.getAlice() == alice)
            return request;
        return null;
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
                && evt.getPlayer().getItemInHand().hasItemMeta())
        {
            if(evt.getPlayer().getItemInHand().getItemMeta()
                    .getDisplayName().equals("Magic Wand"))
            {
                Player bob = evt.getPlayer();
                Player alice = (Player) evt.getRightClicked();

                // check on pending requests from bob,
                // send error based on requeststatus
                if (requests.containsKey(bob))
                {
                    if (!requests.get(bob).getStatus())
                        actionBar.sendActionBarMessage(bob,
                                "&6Only one request a time");
                    else
                    {
                        long remainCool = (requests.get(bob).getRequestTime()
                                + (this.requestCooldown*1000)
                                - System.currentTimeMillis()) / 1000;
                        actionBar.sendActionBarMessage(bob,
                                "&6Cooldown: &4" + remainCool + "&6 Seconds");
                    }
                }
                else if (requests.containsValue(alice)
                        || requests.containsKey(alice))
                {
                    actionBar.sendActionBarMessage(bob,
                            "&4" + alice.getDisplayName() + "&6 is occupied");
                }
                // no pending requests, so create one
                else
                {
                    createRequest(bob, alice);
                }
            } // if "Magic Wand"
        } // if valid action with possible item
    } // onPlayerInteractEntity


    /*
     * Check on player join, if player already has an Halloweenstick/Magic Wand
     * in inventory, otherwise add one
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt)
    {
        ItemStack halloweenstick = util.createItemStack(
                "Magic Wand",
                Material.BLAZE_ROD,
                new String[] {"Right click another Player",
                "to play 'Trick or Treat' !"});

        if (!evt.getPlayer().getInventory().contains(halloweenstick))
            evt.getPlayer().getInventory().addItem(halloweenstick);
    }
}
