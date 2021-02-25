package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
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
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.AttunePower;

public class Galvanize extends CustomCard {

    /*
    * COMMON Atk
    * 1E
    * Deal 8 damage. Increase attunement by 50%.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("Galvanize");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/galvanize.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  //  static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int DAMAGE = 11;
    private static final int MAGIC = 50;
    private static final int UPG_DMG = 4;
    private static final int UPGRADE_MAGIC = 16;


    // /Stat Declaration/

    public Galvanize() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        //tags.add(MainEnum.RITUAL_CARD);
        exhaust = true;

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int ATT = 0; //player's attune
        int mod = 2;
        //addToBot(new GainAttuneAction(1));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            addToBot(new GainAttuneAction(ATT/mod));
        }
    }
    @Override
    public AbstractCard makeCopy() { return new Galvanize(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DMG);
        }
    }

}
