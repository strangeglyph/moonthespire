package org.m14n.moonthespire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.m14n.moonthespire.MoonCharacter;
import org.m14n.moonthespire.PhaseChangeSubscriber;
import org.m14n.moonthespire.PhaseSystem;

import static org.m14n.moonthespire.Mod.cardPath;
import static org.m14n.moonthespire.Mod.makeId;

public class EbbAndFlow extends AbstractMoonCard implements PhaseChangeSubscriber {
    public static final String ID = makeId("EbbAndFlow");
    public static final String IMG = cardPath("moondrop.png");

    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MoonCharacter.Enums.MOON_PURPLE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public EbbAndFlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 5;
        this.isMultiDamage = true;
        this.exhaust = true;
        PhaseSystem.subscribe(this);
    }

    @Override
    public void onRemoveFromMasterDeck() {
        PhaseSystem.unsubscribe(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = AbstractDungeon.player.hand.size();

        //Waxing
        if (PhaseSystem.isWaxing()) {
            // For each card, create 1 damage action.
            for (int i = 0; i < count; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
        //Waning
        if (PhaseSystem.isWaning()) {
            this.addToBot(new DrawCardAction(AbstractDungeon.player, count, false));
        }


        this.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, count, false));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.upgradeDamage(2);
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new EbbAndFlow();
    }

    @Override
    public void onPhaseChange() {
        if (PhaseSystem.isWaxing()) {
            target = CardTarget.ENEMY;
            type = CardType.ATTACK;
        } else {
            target = CardTarget.NONE;
            type = CardType.SKILL;
        }
    }
}

