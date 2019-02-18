package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.ApplyStrengthPower;
import powers.AttunePower;

public class CostlyStrength extends CustomCard {
    /*
    * Uncommon Skill
    * 0E Ritual
    * Ritual. Lose up to 5 Attunement. Gain twice that much (+3) strength next turn only.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("CostlyStrength");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/skill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;
    private int ATT = 0;
    private static int MAGIC = 0;
    private static int UPG_MAGIC = 3;
    private static final int COST = 0;

    // /Stat Declaration/


    public CostlyStrength() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
     //   tags.add(MainEnum.RITUAL_CARD);
        exhaust = true;

    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Find number of attune stacks with max of 5, then draw and reduce by it.
       // AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(1));
        if(p.hasPower(AttunePower.POWER_ID)) {
            ATT = p.getPower(AttunePower.POWER_ID).amount;

            if (ATT > 5)
                ATT = 5;

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AttunePower(AbstractDungeon.player, -ATT), -ATT));

            ATT *= 2;
            //if(upgraded)
              //  ATT += baseMagicNumber;

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ApplyStrengthPower(p, ATT), ATT));

        }

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CostlyStrength();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
