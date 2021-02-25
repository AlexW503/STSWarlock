package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;
import powers.PossessionPower;

public class SoulOpening extends CustomCard {

    /*
    * COMMON
    * 1E Attack
    * Deal 7 damage, if target is possessed apply 3 possession.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SoulOpening");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/opening.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int POS_AMT = 4;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int UPGRADE_MAGIC= 1;

    // /Stat Declaration/

    public SoulOpening() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = POS_AMT;
        magicNumber = baseMagicNumber;
    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new  com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        if(m.hasPower(PossessionPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(m, p, new PossessionPower(m, p, magicNumber), magicNumber));
        }
    }
    @Override
    public AbstractCard makeCopy() { return new SoulOpening(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }

}
