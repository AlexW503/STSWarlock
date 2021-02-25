package cards;

import actions.HexExhaustAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.DelayedPossessionRemovePower;
import powers.PossessionPower;

public class Hex extends CustomCard {

    /*
    * COMMON Skill
    * 1E
    * Apply 4 Possession. Exhaust a card, if it's a curse, apply 3 more.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("Hex");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/transient.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int POS_AMT = 4;
    private static final int SECOND_POS = 3;
    private static final int UPGRADE_MAGIC= 2;

    // /Stat Declaration/

    public Hex() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = POS_AMT;
        magicNumber = baseMagicNumber;

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new ApplyPowerAction(m, p, new PossessionPower(m, p, magicNumber), magicNumber));
        addToBot(new HexExhaustAction(p,m, p, 4, 1, false));

    }
    @Override
    public AbstractCard makeCopy() { return new Hex(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }

}
