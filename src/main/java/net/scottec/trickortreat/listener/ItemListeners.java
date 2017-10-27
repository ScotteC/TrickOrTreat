package net.scottec.trickortreat.listener;

import net.scottec.trickortreat.Items.halloweenstick.HalloweenstickListener;
import net.scottec.trickortreat.TrickOrTreat;

/**
 * Created by fabian on 14.10.16.
 */
public class ItemListeners {
    public ItemListeners(TrickOrTreat.ITrickOrTreat iToT) {
        new HalloweenstickListener(iToT);
    }
}
