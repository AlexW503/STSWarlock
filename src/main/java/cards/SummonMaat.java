package cards;

import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import mod.RitualistMod;
import orbs.BaphOrb;
import orbs.MaatOrb;
import patches.MainEnum;
import powers.AttunePower;

public class SummonMaat extends CustomCard {
    /*
    * Rare Skill
    * 2E Summon
    * Gain ATT block. Next turn gain ATT/3 plated armor. Vessel: Lose 2 plated
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SummonMaat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/summon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 2;
    private static final int DIV = 1;
    private static final int UPG = 6;
    private static final int BASE = 1;
    private int ATT = 0; //player's attune
    // /Stat Declaration/


    public SummonMaat() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BASE;
        baseMagicNumber = block/3;
        exhaust = true;
        tags.add(MainEnum.SUMMON_CARD);

    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            baseBlock = BASE + ATT/DIV;
            if(upgraded)
               baseBlock += UPG;

            block = baseBlock;
            baseMagicNumber = block/3;


        }
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, baseMagicNumber), baseMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new SetPowerZeroAction(p, p, AttunePower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new MaatOrb()));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SummonMaat();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG);
        }
    }

}
