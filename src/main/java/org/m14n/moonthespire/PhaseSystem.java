package org.m14n.moonthespire;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.m14n.moonthespire.relics.PaleWhiteSphere;

public class PhaseSystem {
    // We may want to figure out how to not have this as gross static
    // variables but for now since there is only ever *one* session we
    // are probably fine?
    // We may wanna look into patching this directly into the player class?
    // TODO saving and restoring
    private static boolean isWaxing;
    private static boolean isWaning;

    static void update() {
        // Yes having both waxing and waning active when the piles are of equal
        // size is intentional
        boolean wasWaxing = isWaxing;
        boolean wasWaning = isWaning;

        isWaxing = AbstractDungeon.player.drawPile.size() >= AbstractDungeon.player.discardPile.size();
        isWaning = AbstractDungeon.player.discardPile.size() >= AbstractDungeon.player.drawPile.size();

        if (AbstractDungeon.player.hasRelic(PaleWhiteSphere.ID)) {
            if (wasWaxing && !isWaxing) {
                modifyStrength(-2);
            } else if (!wasWaxing && isWaxing) {
                modifyStrength(2);
            }

            if (wasWaning && !isWaning) {
                modifyDexterity(-2);
            } else if (!wasWaning && isWaning) {
                modifyDexterity(2);
            }
        }
    }

    private static void modifyStrength(int amt) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new StrengthPower(AbstractDungeon.player, amt)
                )
        );
    }
    private static void modifyDexterity(int amt) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new DexterityPower(AbstractDungeon.player, amt)
                )
        );
    }

    static void reset() {
        isWaxing = false;
        isWaning = false;
        update();
    }
}
