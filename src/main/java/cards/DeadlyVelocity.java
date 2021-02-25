package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.DeadlyVelocityPlusPower;
import powers.DeadlyVelocityPower;
import powers.EmptyPalmsPower;

import java.util.Iterator;

public class DeadlyVelocity extends CustomCard {
    /*
    * UNC Power
    * 1E
    * When you play a card gain 1 strength. At the end of this turn, lose that strength.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("DeadlyVelocity");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/velocity.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 2;
    private static final int UPG = 2;
    private static final int AMOUNT = 1;

    // /Stat Declaration/


    public DeadlyVelocity() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = AMOUNT;
        magicNumber = baseMagicNumber;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!upgraded)
            addToBot(new ApplyPowerAction(p, p, new DeadlyVelocityPower(p, magicNumber), magicNumber));
        else
            addToBot(new ApplyPowerAction(p, p, new DeadlyVelocityPlusPower(p, magicNumber), magicNumber));

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DeadlyVelocity();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
           // upgradeBaseCost(UPG);
           rawDescription = UPGRADE_DESCRIPTION;
           initializeDescription();
        }
    }

}
