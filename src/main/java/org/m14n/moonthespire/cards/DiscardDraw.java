package org.m14n.moonthespire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.core.lookup.AbstractConfigurationAwareLookup;
import org.m14n.moonthespire.MoonCharacter;

import static org.m14n.moonthespire.Mod.cardPath;
import static org.m14n.moonthespire.Mod.makeId;

public class DiscardDraw extends AbstractMoonCard {
    public static final String ID = makeId("DiscardDraw");
    public static final String IMG = cardPath("moondrop.png");

    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MoonCharacter.Enums.MOON_PURPLE;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DiscardDraw() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard card : abstractPlayer.drawPile.group) {
                    group.addToTop(card);
                }

                for (AbstractCard card : group.group) {
                    abstractPlayer.drawPile.moveToDiscardPile(card);
                }

                this.isDone = true;
            }
        });
    }
}
