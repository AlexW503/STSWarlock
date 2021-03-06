package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.PossessionPower;

public class HeavyInfection extends CustomCard {

    /*
    * UNC
    * 2E
    * Apply 8 possession. Scales with strength.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("HeavyInfection");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/heavy.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 2;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /Stat Declaration/

    public HeavyInfection() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
      //  ExhaustiveVariable.setBaseValue(this, 2);

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){

        addToBot(new ApplyPowerAction(m, p, new PossessionPower(m, p, damage), damage));
    }
    @Override
    public AbstractCard makeCopy() { return new HeavyInfection(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

}
