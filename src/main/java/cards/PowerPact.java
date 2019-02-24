package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.DelayedStrengthRemovePower;

public class PowerPact extends CustomCard {

    /*
    * UNCOMMON
    * 1E
    * Innate: Deal 10(12) damage and gain 2(3) strength. In three turns lose 4(5) strength.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("PowerPact");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/strike.png");
    public static final String NAME = cardStrings.NAME;

    static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public  static final String DESCRIPTION = cardStrings.DESCRIPTION;
    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 0;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int UPGRADE_MAGIC= 1;
    private static int MAGIC = 2;
    private static int reduce = 2;


    // /Stat Declaration/

    public PowerPact() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;

        isInnate = true;
        exhaust = true;
        reduce = magicNumber+2;


    }

    //Actions the card does
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DelayedStrengthRemovePower(p, 3, reduce)));
        initializeDescription();
    }

    /*
    @Override
    public void applyPowers() {
        super.applyPowers();
        if(rawDescription != null) {
            if (rawDescription.equals(DESCRIPTION))
                rawDescription = DESCRIPTION + reduce + EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }
    */

    @Override
    public AbstractCard makeCopy() { return new PowerPact(); }

    //Upgrade stuff
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}
