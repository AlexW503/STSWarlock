package cards;

import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

public class SummonRerek extends CustomCard {

    /*
    * UNC Summon Rerek
    * 2E
    * Deal Att/2 damage to a random enemy 6 times. VESSEL: Take 2 damage and gain 1 strength.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SummonRerek");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/summonA.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.PURPLE;
    private static final int UPGRADE_COST = 1;
    private static final int MULTI = 2;
    private static final int COUNT = 6;


    private int ATT = 0; //player's attune
    private static final int COST = 2;
    private static final int DAMAGE = 0;
    private static final int UPG = 2;


    // /Stat Declaration/

    public SummonRerek() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = COUNT;
        magicNumber = baseMagicNumber;
        exhaust = true;
        tags.add(MainEnum.SUMMON_CARD);
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        if(AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            baseDamage = DAMAGE + (ATT/MULTI);

        }

        //RitualistMod.logger.info("Made it! " + damage + " ATT:" + ATT);
    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){

        AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), new DamageInfo(p, baseDamage), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new SetPowerZeroAction(p, p, AttunePower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new RerekOrb()));
    }
    @Override
    public AbstractCard makeCopy() { return new SummonRerek(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPG);
            initializeDescription();
        }
    }

}
