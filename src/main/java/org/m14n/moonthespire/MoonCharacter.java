package org.m14n.moonthespire;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.m14n.moonthespire.cards.DiscardDraw;
import org.m14n.moonthespire.cards.MoonDrop;
import org.m14n.moonthespire.relics.PaleWhiteSphere;
import org.m14n.moonthespire.relics.TidalLock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.m14n.moonthespire.Mod.makeId;

public class MoonCharacter extends CustomPlayer {

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_MOON;
        @SpireEnum(name = "MOON_PURPLE_COLOR")
        public static AbstractCard.CardColor MOON_PURPLE;
        @SpireEnum(name = "MOON_PURPLE_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public static final String ID = makeId("MoonCharacter");

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);

    private static final String[] orbTextures = new String[] {
            Mod.energyPath("layer0.png")
    };

    public MoonCharacter(String name, PlayerClass setClass) {
        super(name, Enums.THE_MOON,
                orbTextures, Mod.energyPath("vfx.png"),
                null,
                new SpriterAnimation(Mod.animationPath("moon.scml")));

        initializeClass(null,
                Mod.charPath("placeholder.png"),
                Mod.charPath("placeholder.png"),
                Mod.charPath("placeholder.png"),
                getLoadout(),
                20f, -10f, 220f, 290f,
                new EnergyManager(ENERGY_PER_TURN));

        /*
        loadAnimation(
                Mod.animationPath("skeleton.atlas"),
                Mod.animationPath("skeleton.json"),
                1f
        );
        AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setEndTime(e.getEndTime() * MathUtils.random());
        */

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return new ArrayList<>(Arrays.asList(
                MoonDrop.ID,
                MoonDrop.ID,
                MoonDrop.ID,
                DiscardDraw.ID,
                DiscardDraw.ID,
                DiscardDraw.ID,
                DiscardDraw.ID,
                DiscardDraw.ID,
                DiscardDraw.ID,
                DiscardDraw.ID
        ));
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        UnlockTracker.markRelicAsSeen(TidalLock.ID);
        return new ArrayList<>(Collections.singletonList(
                PaleWhiteSphere.ID
        ));
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0],
                characterStrings.TEXT[0],
                STARTING_HP,
                MAX_HP,
                ORB_SLOTS,
                STARTING_GOLD,
                CARD_DRAW,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false
        );
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[1];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.MOON_PURPLE;
    }

    @Override
    public Color getCardRenderColor() {
        return Colors.MOON_PURPLE_BASIC;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new MoonDrop();
    }

    @Override
    public Color getCardTrailColor() {
        return Colors.MOON_PURPLE_TRAIL;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new MoonCharacter(name, chosenClass);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return Colors.MOON_PURPLE_BASIC;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    @Override
    public String getVampireText() {
        return characterStrings.TEXT[2];
    }

}
