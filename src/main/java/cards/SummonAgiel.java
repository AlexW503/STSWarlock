package cards;

import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
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

public class SummonAgiel extends CustomCard {

    /*
    * STARTER Summon Agiel
    * 2E
    * Deal 8+Att*2 damage. Summons a demon which causes you 3 damage per turn
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SummonAgiel");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/summonA.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.PURPLE;
    private static final int UPGRADE_COST = 1;
    private static final int MULTI = 2;

    private int ATT = 0; //player's attune
    private static final int COST = 2;
    private static final int DAMAGE = 8;


    // /Stat Declaration/

    public SummonAgiel() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MULTI;
        magicNumber = baseMagicNumber;
        exhaust = true;
        tags.add(MainEnum.SUMMON_CARD);
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        if(AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            baseDamage = DAMAGE + (ATT*magicNumber);

        }

        //RitualistMod.logger.info("Made it! " + damage + " ATT:" + ATT);
    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){


        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
       // AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, AttunePower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new SetPowerZeroAction(p, p, AttunePower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new AgielOrb()));
    }
    @Override
    public AbstractCard makeCopy() { return new SummonAgiel(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

}
