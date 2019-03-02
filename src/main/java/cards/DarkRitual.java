package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.AttunePower;
import actions.GainAttuneAction;

public class DarkRitual extends AbstractRitual{
    /*
    * Common Skill
    * 3E Ritual
    * Ritual(+2). Gain 3E, reduce cost by 1 this combat.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("DarkRitual");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/ritual.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;
    private static final int ENERGY = 2;

    private static final int COST = 3;

    // /Stat Declaration/


    public DarkRitual() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = ENERGY;
        magicNumber = baseMagicNumber;
        tags.add(MainEnum.RITUAL_CARD);

    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded)
            AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(1));

        AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(1));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new AdrenalineEffect(), 0.15F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ReduceCostAction(uuid, 1)); //increase E by 1


    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkRitual();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            tags.add(MainEnum.RITUAL_CARD);

        }
    }

}
