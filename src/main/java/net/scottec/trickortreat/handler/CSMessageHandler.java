package net.scottec.trickortreat.handler;

import de.craftstuebchen.api.CsApi;
import de.craftstuebchen.api.Modules.UserManager;
import de.craftstuebchen.api.Modules.messages.MessageManager;
import de.craftstuebchen.api.Modules.messages.messages.actionbar.ActionBarMessage;
import de.craftstuebchen.api.Modules.messages.messages.cooldown.CoolDownItemstackBuilder;
import de.craftstuebchen.api.Modules.messages.messages.title.TitleMessageBuilder;
import org.bukkit.entity.Player;

public class CSMessageHandler {
    private UserManager userManager;

    private ActionBarMessage actionBarMessageBuilder;
    private TitleMessageBuilder titleMessageBuilder;
    private CoolDownItemstackBuilder coolDownItemstackBuilder;

    public CSMessageHandler() {
        MessageManager messageManager = CsApi.getInstance().getModule(MessageManager.class);
        this.userManager = CsApi.getInstance().getModule(UserManager.class);

        this.actionBarMessageBuilder = messageManager.getBaseMessageInstance(ActionBarMessage.class);
        this.titleMessageBuilder = messageManager.getBaseMessageInstance(TitleMessageBuilder.class);
        this.coolDownItemstackBuilder = messageManager.getBaseMessageInstance(CoolDownItemstackBuilder.class);
    }

    public void sendActionBarMessage(Player player, String msg) {
        if (player != null)
            this.actionBarMessageBuilder.sendActionBar(userManager.getPlayer(player.getUniqueId().toString()), msg);
    }

    public void sendTitleMessage(Player player, String title, String subtitle) {
        if (player != null){
            this.titleMessageBuilder.resetTitleMessage(userManager.getPlayer(player.getUniqueId().toString()));
            this.titleMessageBuilder.sendTitleMessage(userManager.getPlayer(player.getUniqueId().toString()), title, subtitle);
        }
    }

    public void sendTitleMessage(Player player, String title, String subtitle, int timeout) {
        if (player != null) {
            this.titleMessageBuilder.resetTitleMessage(userManager.getPlayer(player.getUniqueId().toString()));
            this.titleMessageBuilder.sendTitleMessage(userManager.getPlayer(player.getUniqueId().toString()), title, subtitle, 0, timeout, 0);
        }
    }
}
