package org.m14n.moonthespire.cards;

import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractMoonCard extends CustomCard {
    // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int defaultSecondMagicNumber;
    // And our base stat - the number in it's base state. It will reset to that by default.
    public int defaultBaseSecondMagicNumber;
    // A boolean to check whether the number has been upgraded or not.
    public boolean upgradedDefaultSecondMagicNumber;
    // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)
    public boolean isDefaultSecondMagicNumberModified;

    public AbstractMoonCard(final String id,
                            final String img,
                            final int cost,
                            final CardType type,
                            final CardColor color,
                            final CardRarity rarity,
                            final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isDefaultSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }
    }

    public void upgradeDefaultSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        defaultBaseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedDefaultSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }
}
