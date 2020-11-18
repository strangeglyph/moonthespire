package org.m14n.moonthespire;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.m14n.moonthespire.relics.PaleWhiteSphere;

import java.util.ArrayList;
import java.util.List;

public class PhaseSystem {
    // We may want to figure out how to not have this as gross static
    // variables but for now since there is only ever *one* session we
    // are probably fine?
    // We may wanna look into patching this directly into the player class?
    // TODO saving and restoring
    private static boolean isWaxing;
    private static boolean isWaning;
    private static boolean wasWaxing;
    private static boolean wasWaning;
    private static final List<PhaseChangeSubscriber> subscribers = new ArrayList<>();

    static void update() {
        // Yes having both waxing and waning active when the piles are of equal
        // size is intentional
        wasWaxing = isWaxing;
        wasWaning = isWaning;

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

        for (PhaseChangeSubscriber subscriber : subscribers) {
            subscriber.onPhaseChange();
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

    public static boolean isWaxing() {
        return isWaxing;
    }

    public static boolean isWaning() {
        return isWaning;
    }

    public static boolean wasWaxing() {
        return wasWaxing;
    }

    public static boolean wasWaning() {
        return wasWaning;
    }

    public static void subscribe(PhaseChangeSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public static void unsubscribe(PhaseChangeSubscriber subscriber) {
        subscribers.remove(subscriber);
    }


    static void reset() {
        isWaxing = false;
        isWaning = false;
        update();
    }
}
