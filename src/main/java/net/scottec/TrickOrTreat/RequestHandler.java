package net.scottec.TrickOrTreat;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestHandler {
    private Map<UUID, Request> requests = new HashMap<>();
    private TrickOrTreat.ITrickOrTreat iToT;
    private int requestTimeout;
    private int requestCooldown;

    public RequestHandler(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;

        this.requestTimeout = Config.getCfg()
                .getInt("request.requestTimeout");
        this.requestCooldown = Config.getCfg()
                .getInt("request.requestCooldown");
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
        bob.sendMessage(util.getString("REQUEST_NEW_BOB_TITLE"));
        alice.sendMessage(util.getString("REQUEST_NEW_ALICE_TITLE"));
        alice.sendMessage(util.getString("REQUEST_NEW_ALICE_CLUE"));

        // create schedueled task to remove requestobject from map
        new BukkitRunnable() {
            @Override
            public void run() {
                requests.remove(bob.getUniqueId());
            }
        }.runTaskLater(this.iToT.getPlugin(), (this.requestCooldown * 20));
    }

    /**
     * check if theres a request from bob on alice
     */
    public Request getRequest(Player bob, Player alice) {
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
                bob.sendMessage(util.getString("REQUEST_ONLY_ONE"));
            else {
                // cooldown on next request
                long remainCool = requestCooldown - (System.currentTimeMillis()
                                - requests.get(bob.getUniqueId()).getRequestTime()) / 1000;
                bob.sendMessage(util.getString("REQUEST_COOLDOWN", remainCool));
            }
        }
        // bob is okay, now check alice
        else if ((requests.containsKey(alice.getUniqueId())         // request found?
                && !requests.get(alice.getUniqueId()).getStatus())  // request active?
                || !checkRequests(alice))                           // alice already requested
            bob.sendMessage(util.getString("REQUEST_OCCUPIED", alice.getDisplayName()));
        // no pending requests, so create one
        else
            createRequest(bob, alice);
    }
}
