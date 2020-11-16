package org.m14n.moonthespire;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.m14n.moonthespire.cards.AbstractMoonCard;
import org.m14n.moonthespire.cards.DiscardDraw;
import org.m14n.moonthespire.cards.MoonDrop;
import org.m14n.moonthespire.relics.PaleWhiteSphere;
import org.m14n.moonthespire.relics.TidalLock;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class Mod implements EditCharactersSubscriber, EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        OnStartBattleSubscriber {
    public static final Logger LOGGER = LogManager.getLogger(Mod.class.getName());
    private static final String MOD_ID = "MoonTheSpire";

    public static String cardPath(String resourcePath) {
        return MOD_ID + "/images/cards/" + resourcePath;
    }

    public static String relicPath(String resourcePath) {
        return MOD_ID + "/images/relics/" + resourcePath;
    }

    public static String relicOutlinePath(String resourcePath) {
        return MOD_ID + "/images/relics/outline/" + resourcePath;
    }

    public static String energyPath(String resourcePath) {
        return MOD_ID + "/images/energy/" + resourcePath;
    }

    public static String powerPath(String resourcePath) {
        return MOD_ID + "/images/powers/" + resourcePath;
    }

    public static String eventPath(String resourcePath) {
        return MOD_ID + "/images/events/" + resourcePath;
    }

    public static String charPath(String resourcePath) {
        return MOD_ID + "/images/character/" + resourcePath;
    }

    public static String animationPath(String resourcePath) {
        return MOD_ID + "/animations/" + resourcePath;
    }

    public static String uiPath(String resourcePath) {
        return MOD_ID + "/images/ui/" + resourcePath;
    }

    public static String l10nPath(String lang, String resourcePath) {
        return MOD_ID + "/localization/" + lang + "/" + resourcePath;
    }

    public static String makeId(String name) {
        return MOD_ID + ":" + name;
    }

    public static void initialize() {
        LOGGER.info("M14N presents: ~*~ Moon The Spire ~*~");
        new Mod();
    }

    private Mod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(MoonCharacter.Enums.MOON_PURPLE,
                Colors.MOON_PURPLE_BG, Colors.MOON_PURPLE_BASIC, Colors.MOON_PURPLE_BASIC,
                Colors.MOON_PURPLE_BASIC, Colors.MOON_PURPLE_BASIC, Colors.MOON_PURPLE_TRAIL,
                Colors.MOON_PURPLE_GLOW,
                cardPath("bg/attack.png"), cardPath("bg/skill.png"),
                cardPath("bg/power.png"), cardPath("bg/costs.png"),
                cardPath("bg/attack-large.png"), cardPath("bg/skill-large.png"),
                cardPath("bg/power-larger.png"), cardPath("bg/costs-large.png"),
                cardPath("text-energy-icon.png"));
    }

    @Override
    public void receiveEditCharacters() {
        LOGGER.info("The moon descends to earth.");
        BaseMod.addCharacter(new MoonCharacter(
                        "The Moon",
                        MoonCharacter.Enums.THE_MOON),
                uiPath("select-character.png"),
                charPath("placeholder.png"),
                MoonCharacter.Enums.THE_MOON);
    }

    @Override
    public void receiveEditCards() {
        LOGGER.info("The moon brings its wisdom.");
        /* Kinda ain't working
        new AutoAdd(MOD_ID)
                .packageFilter(AbstractMoonCard.class)
                .setDefaultSeen(true)
                .any(CustomCard.class, (info, card) -> {
                    LOGGER.info("Registering card: " + card.name);
                    BaseMod.addCard(card);
                });
         */
        BaseMod.addCard(new MoonDrop());
        BaseMod.addCard(new DiscardDraw());
    }

    @Override
    public void receiveEditRelics() {
        LOGGER.info("The moon brings its treasures.");
        BaseMod.addRelicToCustomPool(new TidalLock(), MoonCharacter.Enums.MOON_PURPLE);
        BaseMod.addRelicToCustomPool(new PaleWhiteSphere(), MoonCharacter.Enums.MOON_PURPLE);
    }

    @Override
    public void receiveEditStrings() {
        LOGGER.info("The moon speaks all languages (as long as that language is English).");
        BaseMod.loadCustomStringsFile(CardStrings.class, l10nPath("en", "cards.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, l10nPath("en", "character.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, l10nPath("en", "relics.json"));
        //BaseMod.loadCustomStringsFile(PowerStrings.class, l10nPath("en", "powers.json"));
        //BaseMod.loadCustomStringsFile(EventStrings.class, l10nPath("en", "events.json"));
        //BaseMod.loadCustomStringsFile(PotionStrings.class, l10nPath("en", "potions.json"));
        //BaseMod.loadCustomStringsFile(OrbStrings.class, l10nPath("en", "orbs.json"));
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        PhaseSystem.reset();
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(l10nPath("en", "keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(MOD_ID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
