package net.scottec.trickortreat.Items.halloweenstick;

import net.scottec.trickortreat.util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Halloweenstick extends ItemStack {
    public Halloweenstick() {
        this.setType(Material.BLAZE_ROD);
        this.setAmount(1);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(util.getString("HALLOWEENSTICK_NAME"));
        im.setLore(util.getStringList("HALLOWEENSTICK_LORE"));
        this.setItemMeta(im);
        this.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 5);
    }

    public boolean isStick(ItemStack stack) {
        if (stack == null)
            return false;
        ItemStack clone = stack.clone();
        clone.setAmount(1);
        return clone.hashCode() == this.hashCode();
    }
}
