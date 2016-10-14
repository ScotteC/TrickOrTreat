package net.scottec.TrickOrTreat.Listener;

import net.scottec.TrickOrTreat.Items.Halloweenstick.HalloweenstickListener;
import net.scottec.TrickOrTreat.TrickOrTreat;

/**
 * Created by fabian on 14.10.16.
 */
public class ItemListeners {
    public ItemListeners(TrickOrTreat.ITrickOrTreat iToT) {
        new HalloweenstickListener(iToT);
    }
}
