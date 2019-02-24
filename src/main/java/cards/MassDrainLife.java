package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.PossessionPower;

import java.util.Iterator;

public class MassDrainLife extends CustomCard {
    /*
    * RARE ATTACK
    * 2E
    * Deal damage equal to and remove the possession from each enemy. Heal HP equal to 1/3(2) of it. Exhaust.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("MassDrainLife");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/aoe.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 2;
    private static final int MAGIC = 1; // total/magic is healing
    private static final int UPG_COST = 1;
    private static int DMG = 0;
    // /Stat Declaration/


    public MassDrainLife() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        baseDamage = 0; //unused
        exhaust = true;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ReaperEffect()));

        int total = 0; //for healing
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();



        AbstractMonster mo;
        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
            if(mo.hasPower(PossessionPower.POWER_ID))
            {
                DMG = mo.getPower(PossessionPower.POWER_ID).amount;
                total += DMG;
                AbstractDungeon.actionManager.addToBottom(new  com.megacrit.cardcrawl.actions.common.DamageAction(mo, new DamageInfo(p, DMG, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mo, mo, PossessionPower.POWER_ID));

            }
        }

        total = total/magicNumber;
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, total));


    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MassDrainLife();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPG_COST);
            initializeDescription();
        }
    }

}
