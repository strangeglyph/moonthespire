package org.m14n.moonthespire.cards;

import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.m14n.moonthespire.MoonCharacter;

import static org.m14n.moonthespire.Mod.cardPath;
import static org.m14n.moonthespire.Mod.makeId;

public class MoonDrop extends AbstractMoonCard {
    public static final String ID = makeId("MoonDrop");
    public static final String IMG = cardPath("moondrop.png");

    private static final int COST = 0;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MoonCharacter.Enums.MOON_PURPLE;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public MoonDrop() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            target = CardTarget.ALL_ENEMY;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new InstantKillAction(target));
    }

}
