package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;
import powers.AttunePower;
import powers.DoubleVisionPower;

public class DoubleVision extends CustomCard {
    /*
    * RARE Skill
    * 1E Ritual
    * This turn play next 1(2) summon twice.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("DoubleVision");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/double.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] ETH_DESC = cardStrings.EXTENDED_DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 0;
    private static final int MAG = 1;
    private static final int UPG = 1;

    // /Stat Declaration/


    public DoubleVision() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAG;
        magicNumber = baseMagicNumber;
      //  tags.add(MainEnum.RITUAL_CARD);
        exhaust = true;

    }
/*
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(AttunePower.POWER_ID))
            baseMagicNumber = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
    }
*/
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       // addToBot(new GainAttuneAction(1));
      //  magicNumber = baseMagicNumber;
       // addToBot(new GainAttuneAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DoubleVisionPower(p, this.magicNumber), this.magicNumber));

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DoubleVision();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
        }
    }

}
