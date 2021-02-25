package cards;

import actions.IndKnifeAction;
import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import mod.RitualistMod;
import orbs.RerekOrb;
import patches.MainEnum;
import powers.AttunePower;

public class IndiscriminateKnifing extends CustomCard {

    /*
    * UNC Attack
    * 1E
    * Deal 2 damage and apply 1 Vulnerable to a random enemy 3 times.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("IndKnifing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/knifing.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;
    private static final int COUNT = 3;
    private static final int STACKS = 1;

    private int ATT = 0; //player's attune
    private static final int COST = 1;
    private static final int DAMAGE = 2;
    private static final int UPG = 1;


    // /Stat Declaration/

    public IndiscriminateKnifing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = COUNT;
        magicNumber = baseMagicNumber;
    }


    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new IndKnifeAction(AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), new DamageInfo(p, this.baseDamage), this.magicNumber, STACKS));



    }
    @Override
    public AbstractCard makeCopy() { return new IndiscriminateKnifing(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
            initializeDescription();
        }
    }

}
