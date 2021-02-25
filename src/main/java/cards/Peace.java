package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import mod.RitualistMod;
import patches.MainEnum;

public class Peace extends CustomCard {
    /*
     * UNC Skill
     * 1E
     * Retain. Gain 1 intangible. You can't block next turn. Exhaust
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("Peace");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/peace.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;
    private static final int COST = 2;
    private static final int UPG = 1;
    private static final int INTANG = 1;
    private static final int NOBLOCK = 2;



    // /Stat Declaration/


    public Peace() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = INTANG;
        magicNumber = baseMagicNumber;
        retain = true;
        exhaust = true;


    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new NoBlockPower(p, NOBLOCK, false),NOBLOCK));
    }
    @Override
    public void atTurnStart() {
        retain = true;
    }

//    @Override
  //  public void triggerOnEndOfTurnForPlayingCard() {
    //}

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Peace();
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        costForTurn = cost;
        isCostModifiedForTurn = false;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPG);
            initializeDescription();
        }
    }


}
