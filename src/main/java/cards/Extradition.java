package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import powers.ExtraditionPower;
import powers.PossessionPower;

import java.util.Iterator;

public class Extradition extends CustomCard {

    /*
    * UNC attack
    * 1E
    * Deal 9 damage. If the enemy dies before your next turn transfer their Possession to a random enemy.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("Extradition");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/extradition.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /Stat Declaration/

    public Extradition() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        isMultiDamage = true;

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster mo = (AbstractMonster)var1.next();
                if (!mo.isDead && !mo.isDying) {
                    addToBot(new ApplyPowerAction(mo, p, new ExtraditionPower(mo, 1), 1));
                }
            }
        }
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));

    }
    @Override
    public AbstractCard makeCopy() { return new Extradition(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

}
