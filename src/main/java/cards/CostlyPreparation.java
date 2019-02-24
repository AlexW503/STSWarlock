package cards;

import basemod.abstracts.CustomCard;
import characters.Ritualist;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.AttunePower;
import actions.GainAttuneAction;

public class CostlyPreparation extends AbstractRitual {
    /*
    * Uncommon Skill
    * 0E Ritual
    * Ritual(+2). Lose up to 5 sync. Draw that many cards next turn.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("CostlyPreparation");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/skill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;
    private int ATT = 0;
    private static int MAX = 4;
    private static int MAGIC = 1;
    private static int BONUS = 1;
    private static int UPG_M = 1;
    private static final int COST = 0;

    // /Stat Declaration/


    public CostlyPreparation() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        tags.add(MainEnum.RITUAL_CARD);

    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Find number of attune stacks with max of 4, then draw and reduce by it.
        AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(magicNumber));
        if(p.hasPower(AttunePower.POWER_ID)) {
            ATT = p.getPower(AttunePower.POWER_ID).amount;

            if (ATT > MAX) {
                ATT = MAX;
            }

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AttunePower(p, -ATT), -ATT));

            ATT += BONUS; //One free card.

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, ATT), ATT));
        }

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CostlyPreparation();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_M);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
