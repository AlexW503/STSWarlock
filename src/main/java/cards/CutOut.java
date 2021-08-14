package cards;

import actions.CutOutAction;
import actions.EmptyHandHealAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;

public class CutOut extends CustomCard {

    /*
    * UNC RAKE
    * 2E
    * Deal 15 dmg, exh non-rituals in hand, gain 3 hp if hand empty
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("CutOut");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/cutout.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 2;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int HEAL = 3;
    private static final int UPG_M = 1;

    // /Stat Declaration/

    public CutOut() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = HEAL;
        magicNumber = baseMagicNumber;
    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CutOutAction());
        addToBot(new EmptyHandHealAction(magicNumber));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));

    }
    @Override
    public AbstractCard makeCopy() { return new CutOut(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPG_M);
        }
    }

}
