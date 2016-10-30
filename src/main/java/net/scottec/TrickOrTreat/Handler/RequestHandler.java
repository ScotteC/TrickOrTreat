package net.scottec.TrickOrTreat.Handler;

import net.scottec.TrickOrTreat.Config;
import net.scottec.TrickOrTreat.Request;
import net.scottec.TrickOrTreat.TrickOrTreat;
import net.scottec.TrickOrTreat.util;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class RequestHandler {
    private Map<UUID, Request> requests = new HashMap<>();
    private List<UUID> denyRequest = new ArrayList<>();
    private TrickOrTreat.ITrickOrTreat iToT;
    private int requestTimeout;
    private int requestCooldown;

    public RequestHandler(TrickOrTreat.ITrickOrTreat iToT) {
        this.iToT = iToT;

        this.requestTimeout = Config.getCfg()
                .getInt("request.timeout");
        this.requestCooldown = Config.getCfg()
                .getInt("request.cooldown");
    }

    public void setRequestTimings(int requestTimeout, int requestCooldown) {
        if(requestTimeout > 0 && requestCooldown >= 0) {
            this.requestTimeout = requestTimeout;
            this.requestCooldown = requestCooldown;
            Config.getCfg().set("request.timeout", requestTimeout);
            Config.getCfg().set("request.cooldown", requestCooldown);
            Config.saveCfg();
        }
    }

    /*
     * register request creating instance if ToTRequest to save involved
     * players and current time and store it in hashmap
     * also start delayed task to delete request after timeout
     */
    private void createRequest(Player bob, Player alice) {
        requests.put(bob.getUniqueId(),
                new Request(this.iToT, bob.getUniqueId(), alice.getUniqueId(), this.requestTimeout));

        final UUID uuidBob = bob.getUniqueId();
        // create schedueled task to remove requestobject from map
        new BukkitRunnable() {
            @Override
            public void run() {
                requests.remove(uuidBob);
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
        if (denyRequest.contains(alice.getUniqueId())) {
            this.iToT.getCSMessageHandler().sendActionBarMessage(bob, util.getString("REQUEST_TEAMLER"));
        }
        else {
            if (requests.containsKey(bob.getUniqueId())) {
                // check if request is active or on cooldown
                if (!requests.get(bob.getUniqueId()).getStatus())
                    this.iToT.getCSMessageHandler().sendActionBarMessage(bob, util.getString("REQUEST_ONLY_ONE"));
                else {
                    // cooldown on next request
                    long remainCool = requestCooldown - (System.currentTimeMillis()
                            - requests.get(bob.getUniqueId()).getRequestTime()) / 1000;
                    this.iToT.getCSMessageHandler().sendActionBarMessage(bob, util.getString("REQUEST_COOLDOWN", remainCool));
                }
            }
            // bob is okay, now check alice
            else if ((requests.containsKey(alice.getUniqueId())         // request found?
                    && !requests.get(alice.getUniqueId()).getStatus())  // request active?
                    || !checkRequests(alice))                           // alice already requested
                this.iToT.getCSMessageHandler().sendActionBarMessage(bob, util.getString("REQUEST_OCCUPIED", alice.getDisplayName()));
                // no pending requests, so create one
            else
                createRequest(bob, alice);
        }
    }

    public boolean requestToggle(Player player) {
        if(denyRequest.contains(player.getUniqueId()))
            denyRequest.remove(player.getUniqueId());
        else
            denyRequest.add(player.getUniqueId());

        return denyRequest.contains(player.getUniqueId());
    }
}
