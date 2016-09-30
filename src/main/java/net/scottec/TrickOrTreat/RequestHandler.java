package net.scottec.TrickOrTreat;

import de.craftstuebchen.ysl3000.api.messageapi.MessageAPI;
import de.craftstuebchen.ysl3000.api.messageapi.interfaces.IActionbarManager;
import de.craftstuebchen.ysl3000.api.messageapi.interfaces.ITitleManager;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestHandler {
    private Map<UUID, Request> requests = new HashMap<>();
    private TrickOrTreat.ITrickOrTreat iToT;
    private int requestTimeout;
    private long requestCooldown;

    private IActionbarManager actionBar;
    private ITitleManager titleBar;


    public RequestHandler(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;

        this.requestTimeout = Config.getCfg()
                .getInt("request.requestTimeout");
        this.requestCooldown = Config.getCfg()
                .getLong("request.requestCooldown");

        this.actionBar = MessageAPI.inst().getActionbarManager();
        this.titleBar = MessageAPI.inst().getTitleManager();
    }


    /*
     * register request creating instance if ToTRequest to save involved
     * players and current time and store it in hashmap
     * also start delayed task to delete request after timeout
     */
    private void createRequest(Player bob, Player alice) {
        requests.put(bob.getUniqueId(),
                new Request(this.iToT, bob.getUniqueId(), alice.getUniqueId(), this.requestTimeout));

        // inform bob and alice about started request
        titleBar.sendTitleMessageHeader(bob,
                Config.getTxt().getString("request.new.bob.header"));
        titleBar.sendTitleMessageFooter(bob, String.format(
                Config.getTxt().getString("request.new.bob.footer"),
                alice.getDisplayName()));
        titleBar.sendTitleMessageHeader(alice,
                Config.getTxt().getString("request.new.alice.header"));
        titleBar.sendTitleMessageFooter(alice, String.format(
                Config.getTxt().getString("request.new.alice.footer"),
                bob.getDisplayName()));

        alice.sendMessage(Config.getTxt().getString("request.new.alice.clue"));

        // create schedueled task to remove requestobject form map
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                requests.remove(bob, newRequest);
            }
        }.runTaskLater(this.iToT.getPlugin(), (this.requestCooldown * 20));
    }

    /**
     * check if theres a request from bob on alice
     */
    public Request checkRequest(Player bob, Player alice) {
        Request request = requests.get(bob.getUniqueId());
        if (request != null && request.getAlice().equals(alice.getUniqueId()))
            return request;
        return null;
    }

    /**
     * Check if there are open requests on alice
     * @param alice : Player been requestet
     * @return boolean :
     */
    public boolean checkRequests(Player alice) {
        for (Request request : requests.values()) {
            if (request.getAlice().equals(alice.getUniqueId()) && !request.getStatus())
                return request.getStatus();
        }
        return true;
    }

    public void prepareRequest(Player bob, Player alice) {
        // check on pending requests from bob
        // send error based on requeststatus
        if (requests.containsKey(bob.getUniqueId())) {
            // check if request is active or on cooldown
            if (!requests.get(bob.getUniqueId()).getStatus())
        {
            if (!requests.get(bob).getStatus())
                actionBar.sendActionBarMessage(bob,
                        Config.getTxt().getString("request.onlyOne"));
            else
            {
                long remainCool = requestCooldown -
                        (System.currentTimeMillis()
                                - requests.get(bob).getRequestTime()) / 1000;

                actionBar.sendActionBarMessage(bob, String.format(
                        Config.getTxt().getString("request.cooldown"),
                        remainCool));
            }
        }
        // bob is okay, now check alice
        else if ((requests.containsKey(alice.getUniqueId())         // request found?
                && !requests.get(alice.getUniqueId()).getStatus())  // request active?
                || !checkRequests(alice))                           // alice already requested
        {
            actionBar.sendActionBarMessage(bob, String.format(
                    Config.getTxt().getString("request.occupied"),
                    alice.getDisplayName()));
        }

        // no pending requests, so create one
        else
        {
            createRequest(bob, alice);
        }
    }
}
