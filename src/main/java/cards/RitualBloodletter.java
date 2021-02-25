package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;
import actions.GainAttuneAction;

public class RitualBloodletter extends AbstractRitual {

    /*
    * UNCOMMON Ritual Bloodletter
    * 1E Ritual
    * Deal 6 damage, if this is the last card in hand gain 1E and draw 2 cards.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("RitualBloodletter");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/bloodletter.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int ENERGY = 2; //used as magic number
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int UPGRADE_MAGIC = 1; //+ energy
    private static final int DRAW = 2;

    // /Stat Declaration/

    public RitualBloodletter() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = ENERGY;
        magicNumber = baseMagicNumber;
        tags.add(MainEnum.RITUAL_CARD);
    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new GainAttuneAction(1));
        addToBot(new DamageAction(m,
                        new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if(p.hand.size() <= 1) {
            addToBot(new GainEnergyAction(magicNumber));
            addToBot(new DrawCardAction(p, DRAW));
        }

    }
    @Override
    public AbstractCard makeCopy() { return new RitualBloodletter(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();


        }
    }

}
