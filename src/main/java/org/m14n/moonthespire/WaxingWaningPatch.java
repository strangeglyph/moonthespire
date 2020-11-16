package org.m14n.moonthespire;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;

@SpirePatch(
        clz = CardGroup.class,
        method = "applyPowers"
)
public class WaxingWaningPatch {
    @SpirePrefixPatch
    public static void apply(CardGroup instance) {
        System.out.println("Moon | applyPowers patch called");
        PhaseSystem.update();
    }
}
