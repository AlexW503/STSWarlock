package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.PossessionPower;

import java.util.Iterator;

public class DrainStrength extends CustomCard {
    /*
    * UNC Skill
    * 1E
    * Remove all possession from an enemy. Gain that much strength. Exhaust.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("DrainStrength");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/skill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 1;
    private static final int UPG_COST = 0;
    private static final int MAGIC = 10;
    private static final int UPG = 5;


    // /Stat Declaration/


    public DrainStrength() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));

       int str; //amount of strength to gain

        if(m.hasPower(PossessionPower.POWER_ID))
        {
            str = m.getPower(PossessionPower.POWER_ID).amount;
            if(str > baseMagicNumber)
                str = baseMagicNumber;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, str), str));
            if(m.getPower(PossessionPower.POWER_ID).amount <= str)
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, PossessionPower.POWER_ID));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new PossessionPower(m, m, -str), -str));

        }

    }




    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DrainStrength();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
            initializeDescription();
        }
    }

}
