package net.scottec.TrickOrTreat.Items.Halloweenstick;

import net.scottec.TrickOrTreat.util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Halloweenstick extends ItemStack {
    public Halloweenstick() {
        this.setType(Material.BLAZE_ROD);
        ItemMeta im = this.getItemMeta();
        im.setDisplayName(util.getString("HALLOWEENSTICK_NAME"));
        im.setLore(util.getStringList("HALLOWEENSTICK_LORE"));
        this.setItemMeta(im);
        this.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);
    }

    @Override
    public boolean isSimilar(ItemStack stack) {
        ItemStack clone = stack.clone();
        clone.setAmount(1);
        return clone.hashCode() == this.hashCode();
    }
}
