package net.zestyblaze.lycanthropy.client.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.zestyblaze.lycanthropy.client.LycanthropyClientInit;

import java.util.Objects;

///TODO: Implement the GUI screen
public class LycanthropyGuiScreen extends CottonClientScreen {
    public LycanthropyGuiScreen(GuiDescription description) {
        super(description);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int ch, int keyCode, int modifiers) {
        if(LycanthropyClientInit.screenKey.matchesKey(ch, keyCode) || Objects.requireNonNull(client).options.keyInventory.matchesKey(ch, keyCode)) {
            this.onClose();
            return true;
        } else {
            return super.keyPressed(ch, keyCode, modifiers);
        }
    }
}
