package org.m14n.moonthespire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import org.m14n.moonthespire.util.TextureLoader;

import static org.m14n.moonthespire.Mod.*;

public class TidalLock extends CustomRelic {
    public static final String ID = makeId("TidalLock");
    private static final Texture IMG = TextureLoader.getTexture(relicPath("tidallock.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlinePath("tidallock.png"));

    public TidalLock() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
