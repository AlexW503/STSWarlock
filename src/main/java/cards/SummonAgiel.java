package cards;

import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import orbs.AgielOrb;
import patches.MainEnum;
import powers.AttunePower;

public class SummonAgiel extends AbstractSummon {

    /*
    * STARTER Summon Agiel
    * 2E
    * Deal 14+Att*2 damage. Summons a demon which deals 2+Att/5 damage per turn
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SummonAgiel");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/agiel.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;
    private static final int UPGRADE = 3;
    private int MULTI = 2;

    private int ATT = 0; //player's attune
    private static final int COST = 2;
    private static final int DAMAGE = 11;
    private int BASE = 5;
   // private static final int DIV = 3;

    // /Stat Declaration/

    public SummonAgiel() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE;
        magicNumber = baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
        tags.add(MainEnum.SUMMON_CARD);
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        if(AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            if(upgraded)
                MULTI = 3;
            baseDamage = DAMAGE + (ATT*MULTI);
            baseMagicNumber = BASE + ATT;
        }

    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){


        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new ChannelAction(new AgielOrb(baseMagicNumber)));
        addToBot(new SetPowerZeroAction(p, p, AttunePower.POWER_ID));

    }
    @Override
    public AbstractCard makeCopy() { return new SummonAgiel(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE);
            this.upgradedDamage = true;
            MULTI = 3;
            initializeDescription();
        }
    }

}
