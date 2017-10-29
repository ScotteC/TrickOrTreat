package net.scottec.trickortreat.handler;

import de.craftstuebchen.api.CsApi;
import de.craftstuebchen.api.Modules.nms.NMSManager;
import org.bukkit.entity.Player;

public class CSMessageHandler {

    private NMSManager nmsManager;

    public CSMessageHandler() {
        this.nmsManager = CsApi.getInstance().getModule(NMSManager.class);
    }

    public void sendActionBarMessage(Player player, String msg) {
        if (player != null)
            this.nmsManager.getActionBarManager().sendActionBar(player.getUniqueId(), msg);
    }

    public void sendTitleMessage(Player player, String title, String subtitle) {
        if (player != null){
            this.nmsManager.getTitleManager().resetTitleMessage(player.getUniqueId());
            this.nmsManager.getTitleManager().sendTitleMessage(player.getUniqueId(), title, subtitle);
        }
    }

    public void sendTitleMessage(Player player, String title, String subtitle, int timeout) {
        if (player != null) {
            this.nmsManager.getTitleManager().resetTitleMessage(player.getUniqueId());
            this.nmsManager.getTitleManager().sendTitleMessage(player.getUniqueId(), title, subtitle, 0, timeout, 0);
        }
    }
}
