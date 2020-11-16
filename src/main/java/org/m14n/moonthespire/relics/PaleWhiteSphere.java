package org.m14n.moonthespire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.m14n.moonthespire.util.TextureLoader;

import static org.m14n.moonthespire.Mod.*;

public class PaleWhiteSphere extends CustomRelic {
    public static final String ID = makeId("PaleWhiteSphere");
    private static final Texture IMG = TextureLoader.getTexture(relicPath("palewhitesphere.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlinePath("palewhitesphere.png"));

    public PaleWhiteSphere() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
