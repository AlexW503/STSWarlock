package cards;

import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import orbs.AgielOrb;
import orbs.RerekOrb;
import patches.MainEnum;
import powers.AttunePower;
import variables.MagicPlus2;

public class SummonRerek extends AbstractSummon {

    /*
    * UNC Summon Rerek
    * 2E
    * Deal Att/2 damage to a random enemy 6 times. Summon: Take 2 damage and gain 1 strength.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SummonRerek");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/rerek.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;
    private static final int UPGRADE_COST = 1;
    private static final int MULTI = 2;
    private static final int DIV = 5;


    private static final int COST = 3;
    private static final int DAMAGE = 2;
    private static final int UPG = 2;
    private static final int MAGIC = 5;


    private int COUNT = 4;
    private final int  UPG_COUNT = 5;
    private int ATT = 0; //player's attune
    private int LAST = 0;


    // /Stat Declaration/

    public SummonRerek() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        exhaust = true;
        tags.add(MainEnum.SUMMON_CARD);
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        if(AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            baseDamage = DAMAGE + ATT;
            baseMagicNumber = MAGIC + ATT;
            magicNumber = baseMagicNumber;

        }

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new DamageAllEnemiesAction(p,  DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        for (int i = 0; i < this.COUNT; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        addToBot(new ChannelAction(new RerekOrb(magicNumber)));
        addToBot(new SetPowerZeroAction(p, p, AttunePower.POWER_ID));

    }
    @Override
    public AbstractCard makeCopy() { return new SummonRerek(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
           // COUNT = UPG_COUNT;
            //upgradeMagicNumber(UPG);
            upgradeBaseCost(UPG);
           // rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
